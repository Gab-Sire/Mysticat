package com.multitiers.service;

public abstract class PlayableCharacter {
	protected Integer health;
	
	public Boolean isDead() {
		return health <= 0;
	}
}
