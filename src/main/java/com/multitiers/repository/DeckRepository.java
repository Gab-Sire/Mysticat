package com.multitiers.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multitiers.domaine.Deck;

public interface DeckRepository extends JpaRepository<Deck, String>{
}
