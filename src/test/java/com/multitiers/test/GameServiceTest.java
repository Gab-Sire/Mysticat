package com.multitiers.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.multitiers.domaine.entity.User;
import com.multitiers.domaine.ingame.Action;
import com.multitiers.domaine.ingame.AttackAction;
import com.multitiers.domaine.ingame.Game;
import com.multitiers.domaine.ingame.Minion;
import com.multitiers.domaine.ingame.Player;
import com.multitiers.domaine.ingame.SummonAction;

public class GameServiceTest {
	User user1;
	User user2;

	Game game;
	Player player1;
	Player player2;

	Minion slowMinion;
	Minion fastMinion;
	Minion otherFastMinion;

	AttackAction fastAttack;
	AttackAction slowAttack;
	AttackAction otherFastAttack;

	SummonAction summon1;
	SummonAction summon2;

	List<Action> actionListToSort;

	@Before
	public void setUp() throws Exception {
		slowMinion = new Minion();
		slowMinion.setSpeed(0);
		fastMinion = new Minion();
		fastMinion.setSpeed(1);
		otherFastMinion = new Minion();
		otherFastMinion.setSpeed(1);
		otherFastMinion.setHealth(1);

		fastAttack = new AttackAction();
		fastAttack.setSpeed(1);
		slowAttack = new AttackAction();
		slowAttack.setSpeed(0);
		otherFastAttack = new AttackAction();
		otherFastAttack.setSpeed(1);
		
		summon1 = new SummonAction();
		summon2 = new SummonAction();

		actionListToSort = new ArrayList<Action>();
	}

	@After
	public void tearDown() throws Exception {
		user1 = null;
		user2 = null;
		player1 = null;
		player2 = null;

		slowMinion = null;
		fastMinion = null;
		otherFastMinion = null;

		fastAttack = null;
		slowAttack = null;
		otherFastAttack = null;
		summon1 = null;
		summon2 = null;

		actionListToSort = null;
	}

	@Test
	public void twoSummonActionsDontGetSorted() {
		actionListToSort.add(summon1);
		actionListToSort.add(summon2);
		Collections.sort(actionListToSort);
		assertTrue(actionListToSort.get(0).equals(summon1));
		
		actionListToSort.clear();
		actionListToSort.add(summon2);
		actionListToSort.add(summon1);
		assertTrue(actionListToSort.get(1).equals(summon1));
	}
	
	@Test
	public void aSummonActionIsSmallerThanAnAttackAction() {
		actionListToSort.add(fastAttack);
		actionListToSort.add(summon1);
		Collections.sort(actionListToSort);
		assertTrue(actionListToSort.get(0).equals(summon1));
	}
	
	@Test
	public void anAttackActionIsBiggerThanASummonAction() {
		actionListToSort.add(summon1);
		actionListToSort.add(fastAttack);
		Collections.sort(actionListToSort);
		assertTrue(actionListToSort.get(0).equals(summon1));
	}
	
	
	@Test
	public void slowerAttackisBiggerThanFasterAttack() {
		actionListToSort.add(slowAttack);
		actionListToSort.add(fastAttack);
		Collections.sort(actionListToSort);
		assertTrue(actionListToSort.get(0).equals(fastAttack));
	}
	
	@Test
	public void fasterAttackIsSmallerThanSlowerAttack() {
		actionListToSort.add(fastAttack);
		actionListToSort.add(slowAttack);
		Collections.sort(actionListToSort);
		assertTrue(actionListToSort.get(0).equals(fastAttack));
	}
	
	@Test
	public void twoAttacksWithTheSameSpeedDontGetSorted() {
		actionListToSort.add(fastAttack);
		actionListToSort.add(otherFastAttack);
		Collections.sort(actionListToSort);
		assertTrue(actionListToSort.get(0).equals(fastAttack));
		
		actionListToSort.clear();
		actionListToSort.add(otherFastAttack);
		actionListToSort.add(fastAttack);
		assertTrue(actionListToSort.get(1).equals(fastAttack));
	}

}
