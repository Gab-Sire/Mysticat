package com.multitiers.domaine;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="mys_card_car")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "car_type")
public abstract class Card {
	
	@Id
	private String cardId;
	private Integer manaCost;
	private String cardName;
	private String cardDescription;
	
	
	public static MinionCard generateMinion() {
		return null;
	}
	
}
