package com.multitiers.domaine.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "minion")
public class MinionCard extends Card{
	@Column(name="car_power")
	private Integer initialPower;
	@Column(name="car_health")
	private Integer initialHealth;
	@Column(name="car_speed")
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
