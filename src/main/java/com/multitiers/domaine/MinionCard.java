package com.multitiers.domaine;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "minion")
public class MinionCard extends Card{
	
	private Integer initialPower;
	private Integer initialHealth;
	private Integer initialSpeed;
	
	
	
	
}
