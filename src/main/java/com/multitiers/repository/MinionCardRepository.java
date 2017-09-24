package com.multitiers.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multitiers.domaine.MinionCard;

public interface MinionCardRepository extends JpaRepository<MinionCard, String>{
	Set<MinionCard> findByInitialPower(int initialPower);
	Set<MinionCard> findByInitialSpeed(int initialSpeed);
	Set<MinionCard> findByInitialHealth(int initialHealth);
	Set<MinionCard> findByInitialPowerAndInitialHealth(int initialPower, int initialHealth);
	Set<MinionCard> findByInitialPowerAndInitialSpeed(int initialPower, int initialSpeed);
	Set<MinionCard> findByInitialSpeedAndInitialHealth(int initialSpeed, int initialHealth);
	Set<MinionCard> findByInitialPowerAndInitialHealthAndInitialSpeed(int initialPower, int initialHealth, int initialSpeed);
	
}
