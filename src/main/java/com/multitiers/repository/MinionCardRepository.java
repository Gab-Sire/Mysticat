package com.multitiers.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multitiers.domaine.MinionCard;

public interface MinionCardRepository extends JpaRepository<MinionCard, String>{
	Set<MinionCard> findByInitialPower(Integer initialPower);
	Set<MinionCard> findByInitialSpeed(Integer initialSpeed);
	Set<MinionCard> findByInitialHealth(Integer initialHealth);
	Set<MinionCard> findByInitialPowerAndInitialHealth(Integer initialPower, Integer initialHealth);
	Set<MinionCard> findByInitialPowerAndInitialSpeed(Integer initialPower, Integer initialSpeed);
	Set<MinionCard> findByInitialSpeedAndInitialHealth(Integer initialSpeed, Integer initialHealth);
	Set<MinionCard> findByInitialPowerAndInitialHealthAndInitialSpeed(Integer initialPower, Integer initialHealth, Integer initialSpeed);
	
}
