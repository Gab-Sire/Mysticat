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
		if(attacker!=null) {
			if (attackedIndex == HERO_FACE_INDEX) {
				game.addToBattlelog("["+playerDeclaringAttack.getName()+"] "+attacker.getName()+" a attaqué le héro de "
						+opponentPlayer.getName() + " pour "+attacker.getPower() + " point"+((attacker.getPower()>1) ? "s" : "")+ " de dégât.");
				attackFace(opponentPlayer, attacker);
				if(opponentPlayer.getHero().isDead() && game.getWinnerPlayerIndex() == null) {
					game.setWinnerPlayerIndex(playerDeclaringAttackIndex);
				}
			} else {
				attackMinion(opponentPlayerIndex, attackedIndex, attacker, game);
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
	
	private void attackMinion(int opponentPlayerIndex, int attackedIndex, Minion attacker, Game game) {
		Player opponentPlayer = game.getPlayers()[opponentPlayerIndex];
		int attackingPlayerIndex = (opponentPlayerIndex==0) ? 1 : 0;
		Player attackingPlayer = game.getPlayers()[attackingPlayerIndex];
		Minion targetOfTheAttack;
		String log = "";
		targetOfTheAttack = opponentPlayer.getField()[attackedIndex];
		if (targetOfTheAttack != null) {
			game.addToBattlelog("["+attackingPlayer.getName()
			+"] "+attacker.getName()+ " a attaqué "+targetOfTheAttack.getName()+ " pour " + attacker.getPower()+" point"+((attacker.getPower()>1) ? "s" : "")+ " de dégât et s'est pris en retour "
					+targetOfTheAttack.getPower()+" point"+((targetOfTheAttack.getPower()>1) ? "s" : "")+ " de dégât.");
			attackerAndTargetExchangeDamage(attacker, targetOfTheAttack);
			if (attacker.isDead()) {
				log = "["+attackingPlayer.getName() + "] "+attacker.getName()+" est mort et est envoyé au cimetierre.";
				game.addToBattlelog(log);
			}
			if (targetOfTheAttack.isDead()) {
				log = "["+opponentPlayer.getName() + "] "+targetOfTheAttack.getName() +" est mort et est envoyé au cimetierre.";
				game.addToBattlelog(log);
			}
		} else {
			game.addToBattlelog("["+attackingPlayer.getName()+"] La cible de "+attacker.getName()+ " de "+" est morte avant que son attaque se produise.");
		}

	}
	
	private void attackFace(Player opponentPlayer, Minion attacker) {
		Hero targetOfTheAttack;
		targetOfTheAttack = opponentPlayer.getHero();
		targetOfTheAttack.setHealth(targetOfTheAttack.getHealth() - attacker.getPower());
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
