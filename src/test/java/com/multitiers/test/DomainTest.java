package com.multitiers.test;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.multitiers.domaine.entity.Card;
import com.multitiers.domaine.entity.MinionCard;

public class DomainTest {
	
	
	
	@Test
	public void testSortingCards() {
		List<Card> cardListTest = new ArrayList<>();
		List<Card> expectedCardList = new ArrayList<>();
		
		Card card01 = new MinionCard();
		Card card02 = new MinionCard();
		Card card03 = new MinionCard();
		Card card04 = new MinionCard();
		Card card05 = new MinionCard();
		
		card01.setManaCost(0);
		card02.setManaCost(10);
		card03.setManaCost(0);
		card04.setManaCost(9);
		card05.setManaCost(6);
		
		card01.setCardName("A");
		card02.setCardName("Z");
		card03.setCardName("O");
		card04.setCardName("Aooai");
		card05.setCardName("ALLOOXDD");
		
		cardListTest.add(card01);
		cardListTest.add(card02);
		cardListTest.add(card03);
		cardListTest.add(card04);
		cardListTest.add(card05);
		
		expectedCardList.add(card01);
		expectedCardList.add(card03);
		expectedCardList.add(card05);
		expectedCardList.add(card04);
		expectedCardList.add(card02);
		
		Collections.sort(cardListTest);
		
		assertThat(cardListTest).isEqualTo(expectedCardList);
	}
	
	@Test
	public void testSortingCardsByName() {
		List<Card> cardListTest = new ArrayList<>();
		List<Card> expectedCardList = new ArrayList<>();
		
		Card card01 = new MinionCard();
		Card card02 = new MinionCard();
		Card card03 = new MinionCard();
		Card card04 = new MinionCard();
		Card card05 = new MinionCard();
		
		card01.setManaCost(0);
		card02.setManaCost(0);
		card03.setManaCost(0);
		card04.setManaCost(0);
		card05.setManaCost(0);
		
		card01.setCardName("a");
		card02.setCardName("A");
		card03.setCardName("Aa");
		card04.setCardName("aaA");
		card05.setCardName("aa");
		
		cardListTest.add(card01);
		cardListTest.add(card02);
		cardListTest.add(card03);
		cardListTest.add(card04);
		cardListTest.add(card05);
		
		expectedCardList.add(card02);
		expectedCardList.add(card01);
		expectedCardList.add(card03);
		expectedCardList.add(card05);
		expectedCardList.add(card04);
		
		Collections.sort(cardListTest);
		
		assertThat(cardListTest).isEqualTo(expectedCardList);
	}
	
	@Test
	public void testSortingCardsByManaCost() {
		List<Card> cardListTest = new ArrayList<>();
		List<Card> expectedCardList = new ArrayList<>();
		
		Card card01 = new MinionCard();
		Card card02 = new MinionCard();
		Card card03 = new MinionCard();
		Card card04 = new MinionCard();
		Card card05 = new MinionCard();
		
		card01.setManaCost(10);
		card02.setManaCost(0);
		card03.setManaCost(1);
		card04.setManaCost(3);
		card05.setManaCost(2);
		
		card01.setCardName("a");
		card02.setCardName("a");
		card03.setCardName("a");
		card04.setCardName("a");
		card05.setCardName("a");
		
		cardListTest.add(card01);
		cardListTest.add(card02);
		cardListTest.add(card03);
		cardListTest.add(card04);
		cardListTest.add(card05);
		
		expectedCardList.add(card02);
		expectedCardList.add(card03);
		expectedCardList.add(card05);
		expectedCardList.add(card04);
		expectedCardList.add(card01);
		
		Collections.sort(cardListTest);
		
		assertThat(cardListTest).isEqualTo(expectedCardList);
	}

}
