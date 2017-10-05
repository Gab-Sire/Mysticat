package com.multitiers.domaine.ingame;

import com.multitiers.util.Constantes;

public class Hero extends PlayableCharacter{

	public Hero() {
		super();
		this.health = Constantes.STARTING_HERO_HP;
	}
}
