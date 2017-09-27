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
}
