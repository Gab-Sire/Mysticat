package com.multitiers.domaine.ingame;

public class AttackAction extends Action{
	
	private Minion minion;
	private int attackedCell;
	
	public AttackAction() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Minion getMinion() {
		return minion;
	}

	public void setMinion(Minion minion) {
		this.minion = minion;
	}

	public int getAttackedCell() {
		return attackedCell;
	}

	public void setAttackedCell(int attackedCell) {
		this.attackedCell = attackedCell;
	}

	@Override
	void resolve() {
		// TODO Auto-generated method stub
	}
}
