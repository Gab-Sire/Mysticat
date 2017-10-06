package com.multitiers.domaine.ingame;

public class AttackAction extends Action {
	
	private Integer attackerIndex;
	private Integer attackedIndex;
	private Integer speed;

	
	
	public AttackAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getAttackerIndex() {
		return attackerIndex;
	}

	public void setAttackerIndex(Integer attackerIndex) {
		this.attackerIndex = attackerIndex;
	}

	public Integer getAttackedIndex() {
		return attackedIndex;
	}

	public void setAttackedIndex(Integer attackedIndex) {
		this.attackedIndex = attackedIndex;
	}
	
	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
}
