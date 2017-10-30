package com.multitiers.domaine.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "mys_card_car")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "car_type")
public abstract class Card {

	@Id
	@Column(name = "car_id", nullable = false, updatable = false)
	private String cardId;
	@Column(name = "car_mana_cost")
	private Integer manaCost;
	@Column(name = "car_name")
	private String cardName;
	@Column(name = "car_desc")
	private String cardDescription;
	@Column(name="car_img_path")
	private String imagePath;

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public Integer getManaCost() {
		return manaCost;
	}

	public void setManaCost(Integer manaCost) {
		this.manaCost = manaCost;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardDescription() {
		return cardDescription;
	}

	public void setCardDescription(String cardDescription) {
		this.cardDescription = cardDescription;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "Card [cardName=" + cardName + "]";
	}

}
