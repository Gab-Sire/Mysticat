package com.multitiers.test.Ingame;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.multitiers.domaine.ingame.Action;
import com.multitiers.domaine.ingame.ActionList;
import com.multitiers.domaine.ingame.AttackAction;
import com.multitiers.domaine.ingame.SummonAction;
import com.multitiers.domaine.ingame.SurrenderAction;

public class ActionListTest {
	private List <Action> listA;
	private List <Action> listB;
	
	private String playerId;
	
	private ActionList listActionA;
	private ActionList listActionB;
	
	private SummonAction summ1;
	private AttackAction atk1;
	private SurrenderAction surr1;
	
	
	
	@Before
	public void setUp() {
		listA = new ArrayList<Action>();
		listB = new ArrayList<Action>();
		
		atk1 = new AttackAction();
		summ1 = new SummonAction();
		surr1 = new SurrenderAction();
		
		listA.add(summ1);
		listA.add(surr1);
		listB.add(atk1);
		
		playerId = "sdbsd";
		
		listActionA = new ActionList();
		listActionA.setPlayerActions(listA);
		listActionA.setPlayerId(playerId);
		
		listB = new ArrayList<Action>();
		atk1 = new AttackAction();
		listB.add(atk1);
		
		listActionB = new ActionList();
		listActionB.setPlayerActions(listB);
		listActionB.setPlayerId(playerId);
	}
	
	@After
	public void tearDown() {
		listActionA = null;
		listActionB = null;
		listA = null;
		listB = null;
		summ1 = null;
		atk1 = null;
		surr1 = null;
		playerId = null;
	}
	
	@Test
	public void testCombineListAction() {
		listActionA.combineActionList(listActionB);
		assertTrue(listActionA.getPlayerActions().contains(atk1));
	}
	
	@Test
	public void testContainsSurrenderAction() {
		assertTrue(listActionA.containsSurrenderAction());
	}
	
	@Test
	public void isSamePlayerSurrendering() {
		assertTrue(listActionA.isSamePlayerSurrendering(listActionB));
	}

}
