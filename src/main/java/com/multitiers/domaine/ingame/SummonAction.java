package com.multitiers.domaine.ingame;

public class SummonAction extends Action{
	
	private Integer fieldCellWhereTheMinionIsBeingSummoned;
	private Integer indexOfCardInHand;
	
	public SummonAction() {
		super();
	}

	public Integer getFieldCellWhereTheMinionIsBeingSummoned() {
		return fieldCellWhereTheMinionIsBeingSummoned;
	}

	public void setFieldCellWhereTheMinionIsBeingSummoned(Integer fieldCellWhereTheMinionIsBeingSummoned) {
		this.fieldCellWhereTheMinionIsBeingSummoned = fieldCellWhereTheMinionIsBeingSummoned;
	}

	public Integer getIndexOfCardInHand() {
		return indexOfCardInHand;
	}

	public void setIndexOfCardInHand(Integer indexOfCardInHand) {
		this.indexOfCardInHand = indexOfCardInHand;
	}

	@Override
	public void resolve(Game game) {
		if(game.getEndedWithSurrender()) {
			return;
		}
		Player playerSummoningTheMinion = game.getPlayers()[getPlayerIndex()];
		int fieldCell = getFieldCellWhereTheMinionIsBeingSummoned();

		PlayableMinionCard playableMinionCard = (PlayableMinionCard) playerSummoningTheMinion.getHand()
				.get(getIndexOfCardInHand());
		Minion minion = new Minion(playableMinionCard);
		playerSummoningTheMinion.getField()[fieldCell] = minion;
		System.out.println(playerSummoningTheMinion.getName() + " played " + minion.getName() + " on: " + fieldCell);
		
	}

	
}