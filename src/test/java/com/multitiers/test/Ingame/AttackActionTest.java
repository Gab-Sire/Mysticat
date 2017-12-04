package com.multitiers.test.Ingame;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
		
		user1 = new User();
		user2 = new User();
		
		user1.setId("zerx1");
		user2.setId("zerx2");
		
		player1 = new Player(user1);
		player2 = new Player(user2);
		
		Minion[] fieldPlayerOne= {fastMinion,powerfulMinion,bulkyMinion,weakMinion};
		Minion[] fieldPlayerTwo= {fastMinion,powerfulMinion,bulkyMinion,weakMinion};
		player1.setField(fieldPlayerOne);
		player2.setField(fieldPlayerTwo);
		
		game = new Game(player1, player2);
		
		defeatedAttack = new AttackAction();
		survidedAttack= new AttackAction();
		mutualDestructionAttack= new AttackAction();
		snipeAttack= new AttackAction();
		
		defeatedAttack.setPlayerIndex(0);
		survidedAttack.setPlayerIndex(0);
		mutualDestructionAttack.setPlayerIndex(0);
		snipeAttack.setPlayerIndex(0);
		
		defeatedAttack.setAttackingMinionIndex(2);
		survidedAttack.setAttackingMinionIndex(2);
		mutualDestructionAttack.setAttackingMinionIndex(1);
		snipeAttack.setAttackingMinionIndex(0);
		
		defeatedAttack.setTargetIndex(3);
		survidedAttack.setTargetIndex(2);
		mutualDestructionAttack.setTargetIndex(1);
		snipeAttack.setTargetIndex(1);
		
		defeatedAttack.setSpeed(0);;
		survidedAttack.setSpeed(0);
		mutualDestructionAttack.setSpeed(0);
		snipeAttack.setSpeed(0);
		
		actionListToSort = new ArrayList<Action>();
	}

	@After
	public void tearDown() throws Exception {
		user1 = null;
		user2 = null;
		player1 = null;
		player2 = null;
		fastMinion = null;
		powerfulMinion = null;
		bulkyMinion = null;
		weakMinion = null;
		highStat = 10;
		midStat = 5;
		weakStat = 1;

		actionListToSort = null;
	}
	
	
	@Ignore
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
