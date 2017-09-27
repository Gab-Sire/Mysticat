package com.multitiers.service;

public abstract class PlayableCharacter {
	protected Integer health;
	
	public Boolean isDead() {
		return health <= 0;
	}

	public Integer getHealth() {
		return health;
	}

	public void setHealth(Integer health) {
		this.health = health;
	}
	
	
	
}
