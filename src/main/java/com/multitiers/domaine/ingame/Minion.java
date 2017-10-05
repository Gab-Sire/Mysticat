package com.multitiers.domaine.ingame;

public class Minion extends PlayableCharacter{
	private Integer power;
	private Integer speed;
	private Boolean canAttack;
	private PlayableMinionCard cardReference;

	
	public Minion() {
		super();
	}
	public Integer getPower() {
		return power;
	}
	public void setPower(Integer power) {
		this.power = power;
	}
	public Boolean getCanAttack() {
		return canAttack;
	}
	public void setCanAttack(Boolean canAttack) {
		this.canAttack = canAttack;
	}
	public PlayableMinionCard getCardReference() {
		return cardReference;
	}
	public void setCardReference(PlayableMinionCard cardReference) {
		this.cardReference = cardReference;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
}
