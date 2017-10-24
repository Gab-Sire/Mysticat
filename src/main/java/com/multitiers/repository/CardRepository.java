package com.multitiers.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.multitiers.domaine.entity.Card;

public interface CardRepository extends JpaRepository<Card, String> {
	Card findByCardName(String cardName);
	Set<Card> findCardsByManaCost(int manaCost);
}
