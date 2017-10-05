package com.multitiers.domaine.ingame;

public class SummonAction extends Action{
	
	private Integer fieldCell;
	private PlayableMinionCard minionCard;
	
	public SummonAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getFieldCell() {
		return fieldCell;
	}

	public void setFieldCell(Integer fieldCell) {
		this.fieldCell = fieldCell;
	}


	public PlayableMinionCard getMinionCard() {
		return minionCard;
	}


	public void setMinionCard(PlayableMinionCard minionCard) {
		this.minionCard = minionCard;
	}

	@Override
	void resolve() {
		// TODO Auto-generated method stub
		
	}
}
