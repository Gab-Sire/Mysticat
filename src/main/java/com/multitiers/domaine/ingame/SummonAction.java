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

	
}