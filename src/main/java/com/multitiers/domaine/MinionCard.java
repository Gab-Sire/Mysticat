package com.multitiers.domaine;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "minion")
public class MinionCard extends Card{
	private Integer initialPower;
	private Integer initialHealth;
	private Integer initialSpeed;
	
	public Integer getInitialPower() {
		return initialPower;
	}
	public void setInitialPower(Integer initialPower) {
		this.initialPower = initialPower;
	}
	public Integer getInitialHealth() {
		return initialHealth;
	}
	public void setInitialHealth(Integer initialHealth) {
		this.initialHealth = initialHealth;
	}
	public Integer getInitialSpeed() {
		return initialSpeed;
	}
	public void setInitialSpeed(Integer initialSpeed) {
		this.initialSpeed = initialSpeed;
	}
	
}
