package com.multitiers.domaine.ingame;

public class Minion extends PlayableCharacter{
	private String name;
	private String description;
	private Integer power;
	private Integer speed;
	private Boolean canAttack;
	private PlayableMinionCard cardReference;

	
	public Minion() {
		super();
	}
	
	public Minion(PlayableMinionCard minionCard) {
		super();
		this.canAttack = true;
		this.cardReference = minionCard;
		this.power = minionCard.getInitialPower();
		this.health = minionCard.getInitialHealth();
		this.speed = minionCard.getInitialSpeed();
		this.name = minionCard.getName();
		this.description = minionCard.getDescription();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
