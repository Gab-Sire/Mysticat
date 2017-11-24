package com.multitiers.domaine.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum HeroPortrait {
	warriorHero("warriorHero"), 
	wizardHero("wizardHero"), 
	zorroHero("zorroHero"),
	kungfuHero("kungfuHero"), 
	hoodyHero("hoodyHero"),
	chatHero("chatHero"),
	renaissanceHero("renaissanceHero"),
	policeHero("policeHero"),
	prehistoricHero("prehistoricHero"),
	superHero("superHero");
	
	private String hero;
	private HeroPortrait(String hero) {
		this.hero = hero;
	}
	
	
	
}
