package com.multitiers.domaine.ingame;

import java.util.List;

public class AttackAction extends Action {
	private static final int PLAYER_ONE_INDEX = 0;
	private static final int PLAYER_TWO_INDEX = 1;
	private static final int HERO_FACE_INDEX = -1;
	
	private Integer attackingMinionIndex;
	private Integer targetIndex;
	private Integer speed;

	public AttackAction() {
		super();
	}

	public Integer getAttackingMinionIndex() {
		return attackingMinionIndex;
	}

	public void setAttackingMinionIndex(Integer attackingMinionIndex) {
		this.attackingMinionIndex = attackingMinionIndex;
	}

	public Integer getTargetIndex() {
		return targetIndex;
	}

	public void setTargetIndex(Integer targetIndex) {
		this.targetIndex = targetIndex;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	@Override
	public void resolve(Game game) {
		if(game.getEndedWithSurrender()) {
			return;
		}
		Integer playerDeclaringAttackIndex = this.getPlayerIndex();
		Integer opponentPlayerIndex = (playerDeclaringAttackIndex == PLAYER_ONE_INDEX) ? PLAYER_TWO_INDEX
				: PLAYER_ONE_INDEX;
		Player playerDeclaringAttack = game.getPlayers()[playerDeclaringAttackIndex];
		Player opponentPlayer = game.getPlayers()[opponentPlayerIndex];

		int attackerIndex = this.getAttackingMinionIndex();
		int attackedIndex = this.getTargetIndex();
		Minion attacker = playerDeclaringAttack.getField()[attackerIndex];
		if (attacker == null) {
			System.out.println("The minion on " + attackerIndex + " for " + playerDeclaringAttack.getName()
					+ " had previously declared an attack but was killed before it could resolve");
		} 
		else {
			if (attackedIndex == HERO_FACE_INDEX) {
				attackFace(opponentPlayer, attacker);
				if(opponentPlayer.getHero().isDead() && game.getWinnerPlayerIndex() == null) {
					game.setWinnerPlayerIndex(playerDeclaringAttackIndex);
				}
			} else {
				attackMinion(opponentPlayer, attackedIndex, attacker, game);
				sendDeadMinionsToGraveyards(game);
			}
		}
	}
	
	private void attackerAndTargetExchangeDamage(Minion attacker, Minion targetOfTheAttack) {
		attackerTakesDamageFromTarget(attacker, targetOfTheAttack);
		targetTakesDamageFromAttacker(attacker, targetOfTheAttack);
	}
	
	private void targetTakesDamageFromAttacker(Minion attacker, Minion targetOfTheAttack) {
		targetOfTheAttack.setHealth(targetOfTheAttack.getHealth() - attacker.getPower());
	}

	private void attackerTakesDamageFromTarget(Minion attacker, Minion targetOfTheAttack) {
		attacker.setHealth(attacker.getHealth() - ((Minion) targetOfTheAttack).getPower());
	}
	
	private void attackMinion(Player opponentPlayer, int attackedIndex, Minion attacker, Game game) {
		Minion targetOfTheAttack;
		String log = "";
		targetOfTheAttack = opponentPlayer.getField()[attackedIndex];
		if (targetOfTheAttack != null) {
			attackerAndTargetExchangeDamage(attacker, targetOfTheAttack);
			if (attacker.isDead()) {
				log = attacker.getName() + " attacked " + targetOfTheAttack.getName() + " and died.";
				game.addToBattlelog(log);
				System.out.println(attacker.getName() + " attacked " + targetOfTheAttack.getName() + " with power of " + attacker.getPower() + " and died.");
			}
			if (targetOfTheAttack.isDead()) {
				log = "Minion: " + targetOfTheAttack.getName() + " of " + opponentPlayer.getName()
				+ " was killed by " + attacker.getName();
				game.addToBattlelog(log);
				System.out.println("Target on cell: " + attackedIndex + " for " + opponentPlayer.getName()
						+ " was killed by " + attacker.getName());
			}
			if(log == "") {
				log = attacker.getName() + " attacked " + targetOfTheAttack.getName() + " with power of " + attacker.getPower();
				game.addToBattlelog(log);
			}
		} else {
			System.out.println("Target is missing or dead.");
		}

	}
	
	private void attackFace(Player opponentPlayer, Minion attacker) {
		Hero targetOfTheAttack;
		targetOfTheAttack = opponentPlayer.getHero();
		targetOfTheAttack.setHealth(targetOfTheAttack.getHealth() - attacker.getPower());
		if (targetOfTheAttack.isDead()) {
			//System.out.println("Congratz grad, you won.");
		}
	}
	
	private void sendDeadMinionsToGraveyards(Game game) {
		Player[] players = game.getPlayers();
		for(Player player : players) {
			Minion[] field = player.getField();
			List<PlayableCard> graveyard = player.getGraveyard();
			for(int i=0; i<field.length; ++i) {
				Minion minion = field[i];
				if(minion!=null) {
					if(minion.isDead()) {
						field[i] = null;
						graveyard.add(minion.getCardReference());
					}
				}
			}
			player.setGraveyard(graveyard);
			player.setField(field);
		}
	}
}
