package com.multitiers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multitiers.domaine.Card;

public interface CardRepository extends JpaRepository<Card, String> {
	Card findByCardName(String cardName);
}
