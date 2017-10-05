package com.multitiers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.multitiers.domaine.ingame.Game;
import com.multitiers.domaine.ingame.PlayableCard;
import com.multitiers.repository.CardRepository;
import com.multitiers.repository.DeckRepository;
import com.multitiers.repository.MinionCardRepository;
import com.multitiers.repository.UserRepository;

@Service
public class GameService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private MinionCardRepository minionCardRepository;
    
    public GameService() {}
    
    
    public Game deserializeGame(String json) {
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.registerTypeAdapter(PlayableCard.class, new PlayableCardDeserializer()).create();
    	Gson gson = gsonBuilder.create();
    	
    	Game gameFromJson = gson.fromJson(json, Game.class);

    	return gameFromJson;
    }
    
    //Avec liste actions
    public Game updateGame(Game currentGame) {
    	
    	return null;
    }
    
}
