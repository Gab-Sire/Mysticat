package com.multitiers.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multitiers.domaine.Card;
import com.multitiers.domaine.MinionCard;

public interface CardRepository extends JpaRepository<Card, String> {
	Card findByCardName(String cardName);
	Set<Card> findCardsByManaCost(int manaCost);
}
