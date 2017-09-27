package com.multitiers.service;

import com.multitiers.domaine.MinionCard;

public class PlayableMinionCard extends PlayableCard{
	private Integer initialPower;
	private Integer initialHealth;
	private Integer initialSpeed;
	
	public PlayableMinionCard(MinionCard entityCard) {
		this.initialPower = entityCard.getInitialPower();
		this.initialHealth = entityCard.getInitialHealth();
		this.initialSpeed = entityCard.getInitialSpeed();
	}

	public Integer getInitialPower() {
		return initialPower;
	}

	public void setInitialPower(Integer initialPower) {
		this.initialPower = initialPower;
	}

	public Integer getInitialHealth() {
		return initialHealth;
	}

	public void setInitialHealth(Integer initialHealth) {
		this.initialHealth = initialHealth;
	}

	public Integer getInitialSpeed() {
		return initialSpeed;
	}

	public void setInitialSpeed(Integer initialSpeed) {
		this.initialSpeed = initialSpeed;
	}
	
	
}
