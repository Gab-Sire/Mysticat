package com.multitiers.domaine;

import javax.persistence.Column;
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
    @Column(name = "car_id", nullable = false, updatable = false)
	private String cardId;
	@Column(name="car_mana_cost")
	private Integer manaCost;
	@Column(name="car_name")
	private String cardName;
	@Column(name="car_desc")
	private String cardDescription;
	
	
	public static MinionCard generateMinion() {
		return null;
	}
	
}
