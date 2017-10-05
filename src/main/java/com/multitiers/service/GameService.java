package com.multitiers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.multitiers.domaine.entity.User;
import com.multitiers.domaine.ingame.Game;
import com.multitiers.domaine.ingame.PlayableCard;
import com.multitiers.domaine.ingame.Player;
import com.multitiers.repository.CardRepository;
import com.multitiers.repository.DeckRepository;
import com.multitiers.repository.MinionCardRepository;
import com.multitiers.repository.UserRepository;

@Service
public class GameService {
    private static final int NB_OF_PLAYERS_PER_GAME = 2;
	@Autowired
    private UserRepository userRepository;
    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private MinionCardRepository minionCardRepository;
    
    private List<Player> playersInQueue;
    
    public GameService() {}
    
    public void initQueue() {
    	this.playersInQueue = new ArrayList<Player>(); 
    }
    
    public synchronized void addToGameQueue(Player player) {
    	this.playersInQueue.add(player);
    	if(playersInQueue.size()>=NB_OF_PLAYERS_PER_GAME) {
    		Player player1 = playersInQueue.get(0);
    		Player player2 = playersInQueue.get(1);
    		startGame(player1, player2);
    	}
    }
    
    public synchronized void startGame(Player player1, Player player2) {
    	
    }
    
    public Game deserializeGame(String json) {
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.registerTypeAdapter(PlayableCard.class, new PlayableCardDeserializer()).create();
    	Gson gson = gsonBuilder.create();
    	
    	Game gameFromJson = gson.fromJson(json, Game.class);

    	return gameFromJson;
    }
    
    //Avec liste actions
    public Game updateGame(Game currentGame) {
    	
    	return currentGame;
    }
    
}
