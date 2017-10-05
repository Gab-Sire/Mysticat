package com.multitiers.domaine.ingame;

public class SummonAction extends Action{
	
	private Integer fieldCell;
	private Player owner;
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

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	@Override
	void resolve() {
		Minion minion = new Minion();
		minion.setCardReference(this.minionCard);
		minion.setHealth(this.minionCard.getInitialHealth());
	}
}
