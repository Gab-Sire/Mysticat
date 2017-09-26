package com.multitiers.service;

public abstract class PlayableCharacter {
	private Integer health;
	
	public Boolean isDead() {
		return health <= 0;
	}
}
