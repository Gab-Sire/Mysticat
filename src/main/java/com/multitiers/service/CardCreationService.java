
package com.multitiers.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multitiers.domaine.entity.MinionCard;
import com.multitiers.repository.CardRepository;
import com.multitiers.util.ConnectionUtils;

@Service
public class CardCreationService {
	@Autowired
	private CardRepository cardRepository;

	
	@Transactional
	public void initBasicCardSet() {
		generateHalloweenSet();
		generateSuperHeroSet();
		generateMabSet();
		generateMedievalSet();
		generatePopCultureSet();
		generateJobSet();		
		generatePrehistoricalSet();
		generateCatOverSet();
		generateDoodleSet();
		generateEpicSet();
	}

	private void generatePrehistoricalSet() {
		MinionCard charanosaurusRex = createMinionCard("Charanosaurus Rex", 20, 15, 15, 9, "Le prédateur apex.", "img/cardImg/prehistoricalSet/chatTrex.jpg");
		MinionCard chatCromagnon = createMinionCard("Chat de Cromagnon", 3, 10, 2, 2, "Meow smash.", "img/cardImg/prehistoricalSet/chatCromagnon.gif");
		MinionCard tricechatops = createMinionCard("TriceChatops", 12, 15, 3, 5, "Il préfère la nourriture croquante.", "img/cardImg/prehistoricalSet/tricechatTops.jpg");
		MinionCard chatDentSabre = createMinionCard("Chat à dents de sabre", 9, 8, 8, 4, "Un sourire à en mourrir 😂", "img/cardImg/prehistoricalSet/chatDentSabre.jpg");
		MinionCard chatsPoussins = createMinionCard("Chats Poussins", 1, 4, 0, 0, "Qui vient avant l'oeuf ou le chat?", "img/cardImg/prehistoricalSet/hatchlings.jpg");
		
		cardRepository.save(charanosaurusRex);
		cardRepository.save(chatCromagnon);
		cardRepository.save(tricechatops);
		cardRepository.save(chatDentSabre);
		cardRepository.save(chatsPoussins);
	}

	private void generateJobSet() {
		MinionCard chatBanquier = createMinionCard("Chat Banquier", 5, 5, 5, 2, "$", "img/cardImg/jobSet/bankerCat.jpg");
		MinionCard chatPompier = createMinionCard("Chat Pompier", 10, 10, 5, 4, "Viens ici Ashes.", "img/cardImg/jobSet/firefighterCat.jpg");
		MinionCard sherrifMoustache = createMinionCard("Shérrif Moustache", 10, 15, 5, 5, "Vous êtes en état d'arrestation.", "img/cardImg/jobSet/policeCat.jpg");
		MinionCard chatScientifique = createMinionCard("Chat Scientifique", 5, 14, 11, 5, "Non, je ne fabrique pas d'herbe à chat.", "img/cardImg/jobSet/scientistCat.jpg");
		MinionCard professeurMiaou = createMinionCard("Professeur Miaou", 1, 2, 2, 0, "Vous avez beaucoup à apprendre.", "img/cardImg/jobSet/chatErudit.jpg");
		MinionCard chatBucheron = createMinionCard("Bu-chat-ronron", 8, 15, 2, 4, "And my axe. Meow.", "img/cardImg/jobSet/lumberjackCat.png");
		MinionCard chatPecheur = createMinionCard("Chat Pecheur", 2, 7, 6, 2, "Un brave mousaillon.", "img/cardImg/jobSet/fishermanCat.jpg");
		
		cardRepository.save(chatBanquier);
		cardRepository.save(chatPompier);
		cardRepository.save(sherrifMoustache);
		cardRepository.save(chatScientifique);
		cardRepository.save(professeurMiaou);
		cardRepository.save(chatBucheron);
		cardRepository.save(chatPecheur);
	}
	
	@Transactional
	private void generateMedievalSet() {
		// Cartes de Vincent, theme Medieval
		MinionCard chavalier = createMinionCard("Chavalier", 11, 15, 9, 6,
				"Le chavalier le plus redoutable du village", "img/cardImg/medievalSetCards/chavalier.jpg");
		MinionCard chatDragon = createMinionCard("ChatDragon", 25, 25, 5, 10,
				"Ce dragon peut vous pulvériser. Attention.", "img/cardImg/medievalSetCards/dragonCat.jpg");
		MinionCard chatPrincesse = createMinionCard("ChatPrincesse", 10, 5, 5, 3,
				"Ne vous faites pas avoir. Elle est rusée.", "img/cardImg/medievalSetCards/chatPrincesse.jpg");
		MinionCard chatBotte = createMinionCard("ChatBotte", 15, 10, 15, 7,
				"Il décidera de votre sort. Ce sera une chattastrophe", "img/cardImg/medievalSetCards/chatBotte.jpg");
		MinionCard chatFouDuRoi = createMinionCard("Chat Fou du roi", 5, 15, 5, 4, "Toujours là pour vous narguer",
				"img/cardImg/medievalSetCards/chatFou.jpg");
		MinionCard chatPeint = createMinionCard("Chat peint", 5, 20, 15, 7,
				"Personnage tranquille, mais à l'affut de tout. Ne le sous-estimez point.",
				"img/cardImg/medievalSetCards/chatPeint.jpg");
		MinionCard chatBelier = createMinionCard("Chat Belier", 20, 5, 5, 5,
				"Ne vous mettez pas à travers de son chemin, vous allez mourir.",
				"img/cardImg/medievalSetCards/chatBelier.jpg");
		MinionCard chatRoi = createMinionCard("Chat Roi", 5, 15, 10, 5, "Le chat le plus prestigieux du royaume.",
				"img/cardImg/medievalSetCards/chatRoi.jpg");
		MinionCard chatPrince = createMinionCard("Chat prince", 5, 15, 0, 3, "Ne tombez pas sous son charme",
				"img/cardImg/medievalSetCards/chatPrince.jpg");
		MinionCard chatdalf = createMinionCard("Chatdalf", 30, 10, 5, 8,
				"Le chat wizard peut vous transformer en grenouille n'importe quand ...",
				"img/cardImg/medievalSetCards/chatdalf.jpg");
		cardRepository.save(chavalier);
		cardRepository.save(chatDragon);
		cardRepository.save(chatPrincesse);
		cardRepository.save(chatBotte);
		cardRepository.save(chatFouDuRoi);
		cardRepository.save(chatPeint);
		cardRepository.save(chatBelier);
		cardRepository.save(chatRoi);
		cardRepository.save(chatPrince);
		cardRepository.save(chatdalf);
	}

	@Transactional
	private void generateMabSet() {
		// Cartes de Marc-Antoine, mes chats
		MinionCard petitChat = createMinionCard("Petit Chat", 4, 1, 5, 1, "Le petit.", "img/cardImg/mabSet/petitChat.jpg");
		MinionCard madameChat = createMinionCard("Madame Chat", 3, 2, 0, 0, "La madame.", "img/cardImg/mabSet/madameChat.jpg");
		MinionCard chatOrange = createMinionCard("Chat Orange", 5, 2, 3, 1, "L'orange.", "img/cardImg/mabSet/chatOrange.jpg");
		MinionCard autreChat = createMinionCard("Autre Chat", 4, 7, 4, 2, "L'autre.", "img/cardImg/mabSet/autreChat.jpg");
		cardRepository.save(petitChat);
		cardRepository.save(madameChat);
		cardRepository.save(chatOrange);
		cardRepository.save(autreChat);
	}

	@Transactional
	private void generateSuperHeroSet() {
		// Cartes de Marc-Antoine, theme Super Hero et Super Vilain
		MinionCard mechaChat = createMinionCard("Mechachat", 10, 30, 5, 8, "Technologie et mauvaises intentions.",
				"img/cardImg/superheroSet/mechacat.jpg");
		MinionCard dopplemeower = createMinionCard("Dopplemeower", 19, 1, 10, 5, "Une vision terrible.", "img/cardImg/superheroSet/doppleMeower.jpg");
		MinionCard moustacheRousse = createMinionCard("Moustache Rousse", 8, 7, 5, 3,
				"Le pirate le plus dangereux. Il a quand même peur de l'eau.",
				"img/cardImg/superheroSet/moustacheRousse.jpg");
		MinionCard channibalLecter = createMinionCard("Channibal Lecter", 10, 15, 15, 7,
				"Un chat qui mange d'autres chats.", "img/cardImg/superheroSet/channibalLecter.jpg");
		MinionCard ashesThePurrifier = createMinionCard("Ashes, The Purrifier", 25, 30, 0, 10,
				"Démon des temps anciens qui souhaite dominer le monde.",
				"img/cardImg/superheroSet/ashesThePurrifier.jpg");
		MinionCard captainAmerichat = createMinionCard("Captain Americhat", 10, 20, 10, 7, "Protège la nation.",
				"img/cardImg/superheroSet/captainAmerichat.jpg");
		MinionCard superChat = createMinionCard("Super Chat", 15, 25, 10, 9, "Un chat super.",
				"img/cardImg/superheroSet/superChat.png");
		MinionCard goldenClaws = createMinionCard("Golden Claws", 22, 3, 5, 5,
				"Ses griffes légendaires peuvent couper n'importe quoi.", "img/cardImg/superheroSet/goldenClaws.jpg");
		MinionCard redDotCatcher = createMinionCard("Red Dot Catcher", 10, 5, 30, 8,
				"Plus rapide que le point rouge par terre.", "img/cardImg/superheroSet/redDotCatcher.jpeg");
		MinionCard theIncredibleWhisker = createMinionCard("The Incredible Whisker", 5, 10, 10, 4,
				"Son elegance est incomparable.", "img/cardImg/superheroSet/theIncredibleWhisker.jpg");
		cardRepository.save(mechaChat);
		cardRepository.save(dopplemeower);
		cardRepository.save(moustacheRousse);
		cardRepository.save(channibalLecter);
		cardRepository.save(ashesThePurrifier);
		cardRepository.save(captainAmerichat);
		cardRepository.save(superChat);
		cardRepository.save(goldenClaws);
		cardRepository.save(redDotCatcher);
		cardRepository.save(theIncredibleWhisker);
	}

	@Transactional
	private void generatePopCultureSet() {
		// Cartes de Jimmy, theme Comic Relieve (PopCulture/popStar)
		//createMinionCard(name, power, health, speed, manaCost, desc, imagePath)
		MinionCard breakingCat = createMinionCard("Breaking Cat", 5, 10, 5, 3, "I'm not in the meth business. I'm in the empire business.", "img/cardImg/popcultureSet/BreakingCat.jpg");
		MinionCard princessLeia = createMinionCard("Princess Leia", 7, 5, 8, 3, "Can't believe I was so foolish to think I could find Luke and bring him home.", "img/cardImg/popcultureSet/Leia.jpg");
		MinionCard cattyGaga = createMinionCard("Catty Gaga", 4, 11, 5, 3, "I was born this way hey!", "img/cardImg/popcultureSet/LadyGaga.jpg");
		MinionCard kittyPerry = createMinionCard("Kitty Perry", 4, 2, 4, 1, "Take me, ta-ta-take me, Wanna be your victim, Ready for abduction, Boy, you're an alien", "img/cardImg/popcultureSet/kitty-perry.jpg");
		MinionCard nikkiMinou = createMinionCard("Nikki Minou", 4, 5, 1, 1, "Hit 'em with the oh now that's just ridiculous, We could've cleared up all these particulars", "img/cardImg/popcultureSet/Nikki_Mioui.jpg");
		MinionCard catSparrow = createMinionCard("Cat Sparrow", 10, 10, 10, 5, "Why the rum is always gone. ", "img/cardImg/popcultureSet/CatSperow.jpg");
		MinionCard elvisChat = createMinionCard("Elvis Cat", 22, 9, 4, 6, "Wise men sayOnly fools rush inBut I can't help falling in love with you", "img/cardImg/popcultureSet/elvis.png");
		MinionCard miouwlyCyrus = createMinionCard("Miouwly Cyrus", 9, 11, 5, 4, "I came in like a wrecking ball", "img/cardImg/popcultureSet/MileyCirus.jpg");
		MinionCard dieBunny = createMinionCard("DIE Bunny", 12, 5, 8, 4, "STUPID HUMAIN, A Battery Up My B***, You must All die, DIE DIE DIE DIE", "img/cardImg/popcultureSet/Sellout.jpg");
		MinionCard thrillerCat = createMinionCard("Thriller Cat", 12, 11, 12, 6, "Cause this is thriller, Thriller night, And no one’s gonna save you", "img/cardImg/popcultureSet/TrillerCat.jpg");
		cardRepository.save(breakingCat);
		cardRepository.save(princessLeia);
		cardRepository.save(cattyGaga);
		cardRepository.save(kittyPerry);
		cardRepository.save(nikkiMinou);
		cardRepository.save(catSparrow);
		cardRepository.save(elvisChat);
		cardRepository.save(miouwlyCyrus);
		cardRepository.save(dieBunny);
		cardRepository.save(thrillerCat);
		
	}
	
	@Transactional
	private void generateDoodleSet() {
		// Cartes de Jimmy, theme Doodle

		MinionCard frigo = createMinionCard("Chat Frigo", 3, 5 , 2, 1, "Je suis fiere de toi", "img/cardImg/DoodleSet/fridgeCat.jpeg");
		MinionCard kid = createMinionCard("Chat dessin Enfant", 1, 7 , 2, 1, "Mon premier dessin", "img/cardImg/DoodleSet/kiddrawing.png");
		MinionCard chat = createMinionCard("Chat01", 1, 6, 3, 1, "Chat Chat chat", "img/cardImg/DoodleSet/cutelineCAt.png");
		MinionCard clean = createMinionCard("Chat de Coeur", 3, 5, 7, 2, "Food Food Food", "img/cardImg/DoodleSet/heartCat.jpg");
		MinionCard icon = createMinionCard("Icon", 9, 13, 3, 4, "Pay 2 win", "img/cardImg/DoodleSet/iconCat.png");
		MinionCard Painture = createMinionCard("Painture d'un chat", 5, 15, 5, 4, "Painture", "img/cardImg/DoodleSet/paintingCat.jpeg");
		MinionCard Mozaichat = createMinionCard("Mozaichat",7 , 13, 10, 5, "Art", "img/cardImg/DoodleSet/mozaicCat.jpg");
		MinionCard plusieur = createMinionCard("Many Chat", 5, 25, 5, 6, "Union fait la force", "img/cardImg/DoodleSet/ManyCat.png");

		cardRepository.save(frigo);
		cardRepository.save(kid);
		cardRepository.save(chat);
		cardRepository.save(clean);
		cardRepository.save(icon);
		cardRepository.save(Painture);
		cardRepository.save(Mozaichat);
		cardRepository.save(plusieur);
	}
	
	@Transactional
	private void generateEpicSet() {
		// Cartes de Jimmy, theme Epic
		MinionCard sleeping = createMinionCard("Snooze", 5, 34 , 1, 7, "zZ zZ zZ", "img/cardImg/EpicSet/SleepingCat.gif");
		MinionCard dancing = createMinionCard("Dancing Chat", 8, 12 , 15, 6, "Let's dance", "img/cardImg/EpicSet/DancingCat.gif");
		MinionCard RidingChat = createMinionCard("RidingChat", 8, 8 , 24, 7, "Yhay", "img/cardImg/EpicSet/RidingCat.gif");
		MinionCard beat = createMinionCard("Beat Chat", 8, 24, 13, 8, "Beat on the music", "img/cardImg/EpicSet/BeatCat.gif");
		MinionCard Winter = createMinionCard("Winter Chat", 8, 8, 19, 6, "Let the storm rage on, The cold never bothered me anyway", "img/cardImg/EpicSet/SuperCuteCat.gif");
		MinionCard padding = createMinionCard("Padding Chat", 8, 22, 15, 8, "My belly next, My belly next", "img/cardImg/EpicSet/Padding.gif");
		MinionCard skatterA = createMinionCard("Grumpy Bat Lord", 10, 20, 15, 8, "Go my minion", "img/cardImg/EpicSet/BatLordCat.gif");
		MinionCard jedi = createMinionCard("Nia", 10, 35, 5, 9, "Nia Nia Nia Nia Nia", "img/cardImg/EpicSet/RainbowCat.gif");
		MinionCard nia = createMinionCard("Chat Jedi", 10, 34, 6, 9, "Use the force Luke", "img/cardImg/EpicSet/catSaber.gif");
		MinionCard gun = createMinionCard("Chat Gun",10 , 40, 5, 10, "AHAHAHAHHAHAHAHAHA", "img/cardImg/EpicSet/GunCat.gif");
		cardRepository.save(sleeping);
		cardRepository.save(dancing);
		cardRepository.save(beat);
		cardRepository.save(padding);
		cardRepository.save(RidingChat);
		cardRepository.save(Winter);
		cardRepository.save(skatterA);
		cardRepository.save(jedi);
		cardRepository.save(nia);
		cardRepository.save(gun);
		
	}
	
	@Transactional
	private void generateCatOverSet() {
		// Cartes de Jimmy, theme Photo Cat over Quelque chose
		MinionCard turkey = createMinionCard("Chat Turkey", 3, 6 , 1, 1, "Eat Me", "img/cardImg/CatOverSet/TurkeyCat.jpg");
		MinionCard sushi = createMinionCard("Chat sushi", 1, 8 , 1, 1, "Eat Me", "img/cardImg/CatOverSet/SushiCat.jpeg");
		MinionCard mop = createMinionCard("Chat Mop", 1, 5, 4, 1, "No. Not the bucket", "img/cardImg/CatOverSet/Mop.jpg");
		MinionCard clean = createMinionCard("Chat Clean", 4, 6, 5, 2, "Spot less", "img/cardImg/CatOverSet/CleanningCat.jpg");
		MinionCard swim = createMinionCard("Chat Swim", 4, 5, 6, 2, "Dive in", "img/cardImg/CatOverSet/swimming.jpg");
		MinionCard skatterA = createMinionCard("Chat Skater Trick", 5, 10, 5, 3, "Grab", "img/cardImg/CatOverSet/SkateBoardTrickCat.jpg");
		MinionCard ariel = createMinionCard("Chariel", 12, 9, 4, 4, "Under the sea, Nobody beat us, Fry us and eat us, In fricassee", "img/cardImg/CatOverSet/MermaidCat.jpg");
		MinionCard Chatraigner = createMinionCard("Chatraigner",10 , 15, 5, 5, "Food Food Food! Kill Food", "img/cardImg/CatOverSet/SpiderMonsterCat.jpeg");
		cardRepository.save(turkey);
		cardRepository.save(sushi);
		cardRepository.save(swim);
		cardRepository.save(mop);
		cardRepository.save(clean);
		cardRepository.save(skatterA);
		cardRepository.save(ariel);
		cardRepository.save(Chatraigner);
		
	}
	
	@Transactional
	private void generateHalloweenSet() {
		// Cartes de Gabriel, theme Halloween
		MinionCard chatMomie = createMinionCard("Chat Momie", 3, 4, 3, 1, "La malédiction du pharaon",
				"img/cardImg/halloweenSetCards/chat_momie.jpg");
		MinionCard chatSouris = createMinionCard("Chat-Souris", 3, 3, 4, 1, "Vous avez dit chat-souris?",
				"img/cardImg/halloweenSetCards/chat_souris.jpg");
		MinionCard chatFantome = createMinionCard("Chat Fantome", 2, 6, 2, 1, "Boo",
				"img/cardImg/halloweenSetCards/chat_fantome.jpg");
		MinionCard chatNoir = createMinionCard("Chat Noir", 2, 4, 4, 1,
				"Si c'est vendredi 13, bonne chance pour la suite", "img/cardImg/halloweenSetCards/chat_noir.jpg");
		MinionCard jackOChat = createMinionCard("Jack-O-Chat", 4, 6, 5, 2, "Bonne carte sans l'ombre d'un doute",
				"img/cardImg/halloweenSetCards/jack_o_chat.jpg");
		MinionCard apprentiSorcier = createMinionCard("Apprenti-Sorcier", 5, 6, 4, 2, "Abra Kadrachat !",
				"img/cardImg/halloweenSetCards/apprenti_sorcier.jpg");
		MinionCard chatZombie = createMinionCard("Chat Zombie", 6, 4, 5, 2, "OMFG BBQ",
				"img/cardImg/halloweenSetCards/chat_zombie.jpg");
		MinionCard frankenChat = createMinionCard("FrankenChat", 7, 8, 5, 3,
				"Combien de vies de chats en échange de cette créature ?",
				"img/cardImg/halloweenSetCards/frankenchat.jpg");
		MinionCard chatPossede = createMinionCard("Chat Possédé", 9, 6, 5, 3, "Ehhh boy",
				"img/cardImg/halloweenSetCards/chat_possede.jpg");
		MinionCard chanatique = createMinionCard("Chanatique", 6, 8, 6, 3, "Tellement myst�rieux...",
				"img/cardImg/halloweenSetCards/chanatique.jpg");
		
		cardRepository.save(chatMomie);
		cardRepository.save(chatSouris);
		cardRepository.save(chatFantome);
		cardRepository.save(chatNoir);
		cardRepository.save(jackOChat);
		cardRepository.save(apprentiSorcier);
		cardRepository.save(chatZombie);
		cardRepository.save(frankenChat);
		cardRepository.save(chatPossede);
		cardRepository.save(chanatique);
	}
	
	@Transactional
	public MinionCard createMinionCard(String name, Integer power, Integer health, Integer speed, Integer manaCost,
			String desc, String imagePath) {
		MinionCard card = new MinionCard();
		card.setCardId(ConnectionUtils.generateUUID().toString());
		card.setCardName(name);
		card.setInitialHealth(health);
		card.setInitialPower(power);
		card.setInitialSpeed(speed);
		card.setManaCost(manaCost);
		card.setCardDescription(desc);
		card.setImagePath(imagePath);
		return card;
	}
}

