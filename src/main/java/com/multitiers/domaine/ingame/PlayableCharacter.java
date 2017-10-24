package com.multitiers.domaine.ingame;

import java.beans.Transient;

public abstract class PlayableCharacter {
	protected Integer health;
	
	@Transient
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
