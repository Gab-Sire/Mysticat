package com.multitiers.domaine.ingame;

public class PlayableCard {
	protected Integer manaCost;
	protected String name;
	protected String description;
	
	public Integer getManaCost() {
		return manaCost;
	}
	public void setManaCost(Integer manaCost) {
		this.manaCost = manaCost;
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
