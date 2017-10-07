package com.multitiers.domaine.ingame;

public class SummonAction extends Action{
	
	private Integer fieldCellWhereTheMinionIsBeingSummoned;
	private PlayableMinionCard minionCard;
	
	public SummonAction() {
		super();
	}

	public Integer getFieldCellWhereTheMinionIsBeingSummoned() {
		return fieldCellWhereTheMinionIsBeingSummoned;
	}

	public void setFieldCellWhereTheMinionIsBeingSummoned(Integer fieldCellWhereTheMinionIsBeingSummoned) {
		this.fieldCellWhereTheMinionIsBeingSummoned = fieldCellWhereTheMinionIsBeingSummoned;
	}

	public PlayableMinionCard getMinionCard() {
		return minionCard;
	}

	public void setMinionCard(PlayableMinionCard minionCard) {
		this.minionCard = minionCard;
	}	
}