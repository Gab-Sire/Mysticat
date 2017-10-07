package com.multitiers.domaine.ingame;

public class AttackAction extends Action {

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
}
