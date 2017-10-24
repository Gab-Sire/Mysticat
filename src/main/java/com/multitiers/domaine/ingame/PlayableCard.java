package com.multitiers.domaine.ingame;

public abstract class PlayableCard {
	protected Integer manaCost;
	protected String name;
	protected String description;
	//Flagged durant le tour du joueur. Une carte played sera enlevee de la main
	protected Boolean played;
	protected String imagePath;
	
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
	public Boolean getPlayed() {
		return played;
	}
	public void setPlayed(Boolean played) {
		this.played = played;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}
