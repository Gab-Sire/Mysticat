package com.multitiers.domaine.entity;

public enum HeroPortrait {
	warriorHero("warriorHero"), 
	wizardHero("wizardHero"), 
	zorroHero("zorroHero");
	
	private String hero;
	private HeroPortrait(String hero) {
		this.hero = hero;
	}
	
}
