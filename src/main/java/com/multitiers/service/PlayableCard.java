package com.multitiers.service;

public abstract class PlayableCard {
	private Integer manaCost;
	private String name;
	private String description;
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
