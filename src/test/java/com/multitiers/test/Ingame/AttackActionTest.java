package com.multitiers.test.Ingame;

import static org.junit.Assert.*;

import java.util.ArrayList;
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

public class AttackActionTest {
	User user1;
	User user2;

	Game game;
	Player player1;
	Player player2;

	Minion fastMinion;
	Minion powerfulMinion;
	Minion bulkyMinion;
	Minion weakMinion;
	int highStat;
	int midStat;
	int weakStat;

	AttackAction defeatedAttack;
	AttackAction survidedAttack;
	AttackAction mutualDestructionAttack;
	AttackAction snipeAttack;

	List<Action> actionListToSort;
	
	@Before
	public void setUp() throws Exception {
		fastMinion = new Minion();
		powerfulMinion = new Minion();
		bulkyMinion = new Minion();
		weakMinion = new Minion();
		highStat = 10;
		midStat = 5;
		weakStat = 1;
		
		fastMinion.setPower(midStat);
		fastMinion.setHealth(highStat);
		fastMinion.setSpeed(highStat);
		
		powerfulMinion.setPower(midStat);
		powerfulMinion.setHealth(midStat);
		powerfulMinion.setSpeed(midStat);
		
		bulkyMinion.setPower(weakStat);
		bulkyMinion.setHealth(midStat);
		bulkyMinion.setSpeed(weakStat);
		
		weakMinion.setPower(weakStat);
		weakMinion.setHealth(weakStat);
		weakMinion.setSpeed(weakStat);

		actionListToSort = new ArrayList<Action>();
	}

	@After
	public void tearDown() throws Exception {
		user1 = null;
		user2 = null;
		player1 = null;
		player2 = null;


		actionListToSort = null;
	}
	
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
