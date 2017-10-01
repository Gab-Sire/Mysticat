package com.multitiers.domaine.ingame;

public abstract class PlayableCard {
	protected Integer manaCost;
	protected String name;
	protected String description;
	protected String key;
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
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
}
