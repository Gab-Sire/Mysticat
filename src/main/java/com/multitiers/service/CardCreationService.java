package com.multitiers.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multitiers.domaine.entity.MinionCard;
import com.multitiers.repository.CardRepository;
import com.multitiers.repository.DeckRepository;
import com.multitiers.repository.MinionCardRepository;
import com.multitiers.repository.UserRepository;
import com.multitiers.util.ConnectionUtils;

@Service
public class CardCreationService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DeckRepository deckRepository;
	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private MinionCardRepository minionCardRepository;
	
	@Transactional
	public void initBasicCardSet() {
		generateHalloweenSet();
		generateSuperHeroSet();
		generateMabSet();
		generateMedievalSet();
		generatePopCultureSet();
	}
	
	@Transactional
	private void generateMedievalSet() {
		// Cartes de Vincent, theme Medieval
		MinionCard minionCard25 = createMinionCard("Chavalier", 7, 8, 5, 10,
				"Le chavalier le plus redoutable du village", "img/cardImg/medievalSetCards/chavalier.jpg");
		MinionCard minionCard26 = createMinionCard("ChatDragon", 15, 20, 10, 15,
				"Ce dragon peut vous pulvériser. Attention.", "img/cardImg/medievalSetCards/dragonCat.jpg");
		MinionCard minionCard27 = createMinionCard("ChatPrincesse", 5, 5, 5, 3,
				"Ne vous faites pas avoir. Elle est rusée.", "img/cardImg/medievalSetCards/chatPrincesse.jpg");
		MinionCard minionCard28 = createMinionCard("ChatBotte", 8, 15, 3, 7,
				"Il décidera de votre sort. Ce sera une chattastrophe", "img/cardImg/medievalSetCards/chatBotte.jpg");
		MinionCard minionCard29 = createMinionCard("Chat Fou du roi", 4, 2, 4, 4, "Toujours là pour vous narguer",
				"img/cardImg/medievalSetCards/chatFou.jpg");
		MinionCard minionCard30 = createMinionCard("Chat peint", 5, 7, 6, 7,
				"Personnage tranquille, mais à l'affut de tout. Ne le sous-estimez point.",
				"img/cardImg/medievalSetCards/chatPeint.jpg");
		MinionCard minionCard31 = createMinionCard("Chat Belier", 15, 10, 5, 12,
				"Ne vous mettez pas à travers de son chemin, vous allez mourir.",
				"img/cardImg/medievalSetCards/chatBelier.jpg");
		MinionCard minionCard32 = createMinionCard("Chat Roi", 6, 7, 5, 5, "Le chat le plus prestigieux du royaume.",
				"img/cardImg/medievalSetCards/chatRoi.jpg");
		MinionCard minionCard33 = createMinionCard("Chat prince", 8, 6, 4, 3, "Ne tombez pas sous son charme",
				"img/cardImg/medievalSetCards/chatPrince.jpg");
		MinionCard minionCard34 = createMinionCard("Chatdalf", 5, 10, 10, 4,
				"Le chat wizard peut vous transformer en grenouille n'importe quand ...",
				"img/cardImg/medievalSetCards/chatdalf.jpg");
		cardRepository.save(minionCard25);
		cardRepository.save(minionCard26);
		cardRepository.save(minionCard27);
		cardRepository.save(minionCard28);
		cardRepository.save(minionCard29);
		cardRepository.save(minionCard30);
		cardRepository.save(minionCard31);
		cardRepository.save(minionCard32);
		cardRepository.save(minionCard33);
		cardRepository.save(minionCard34);
	}

	@Transactional
	private void generateMabSet() {
		// Cartes de Marc-Antoine, mes chats
		MinionCard minionCard21 = createMinionCard("Petit Chat", 4, 1, 5, 1, "Le petit.", "img/cardImg/mabSet/petitChat.jpg");
		MinionCard minionCard22 = createMinionCard("Madame Chat", 5, 3, 3, 1, "La madame.", "img/cardImg/mabSet/madameChat.jpg");
		MinionCard minionCard23 = createMinionCard("Chat Orange", 5, 2, 3, 1, "L'orange.", "img/cardImg/mabSet/chatOrange.jpg");
		MinionCard minionCard24 = createMinionCard("Autre Chat", 5, 5, 5, 2, "L'autre.", "img/cardImg/mabSet/autreChat.jpg");
		cardRepository.save(minionCard21);
		cardRepository.save(minionCard22);
		cardRepository.save(minionCard23);
		cardRepository.save(minionCard24);
	}

	@Transactional
	private void generateSuperHeroSet() {
		// Cartes de Marc-Antoine, theme Super Hero et Super Vilain
		MinionCard minionCard11 = createMinionCard("MechaChat", 10, 30, 5, 8, "Technologie et mauvaises intentions.",
				"img/cardImg/superheroSet/mechacat.jpg");
		MinionCard minionCard12 = createMinionCard("DoppleMeower", 10, 1, 19, 5, "Une vision terrible.", "img/cardImg/superheroSet/doppleMeower.jpg");
		MinionCard minionCard13 = createMinionCard("Moustache Rousse", 5, 10, 5, 3,
				"Le pirate le plus dangereux. Il a quand même peur de l'eau.",
				"img/cardImg/superheroSet/moustacheRousse.jpg");
		MinionCard minionCard14 = createMinionCard("Channibal Lecter", 10, 15, 15, 7,
				"Un chat qui mange d'autres chats.", "img/cardImg/superheroSet/channibalLecter.jpg");
		MinionCard minionCard15 = createMinionCard("Ashes, The Purrifier", 25, 30, 0, 10,
				"Démon des temps anciens qui souhaite dominer le monde.",
				"img/cardImg/superheroSet/ashesThepurrifier.jpg");
		MinionCard minionCard16 = createMinionCard("Captain AmeriChat", 10, 20, 10, 7, "Protège la nation.",
				"img/cardImg/superheroSet/captainAmerichat.jpg");
		MinionCard minionCard17 = createMinionCard("Super Chat", 15, 25, 5, 8, "Un chat super.",
				"img/cardImg/superheroSet/superChat.png");
		MinionCard minionCard18 = createMinionCard("Golden Claws", 20, 5, 5, 5,
				"Ses griffes légendaires peuvent couper n'importe quoi.", "img/cardImg/superheroSet/goldenClaws.jpg");
		MinionCard minionCard19 = createMinionCard("Red Dot Catcher", 10, 5, 30, 8,
				"Plus rapide que le point rouge par terre.", "img/cardImg/superheroSet/redDotCatcher.jpeg");
		MinionCard minionCard20 = createMinionCard("The Incredible Whisker", 5, 10, 10, 4,
				"Son elegance est incomparable.", "img/cardImg/superheroSet/theIncredibleWhisker.jpg");
		cardRepository.save(minionCard11);
		cardRepository.save(minionCard12);
		cardRepository.save(minionCard13);
		cardRepository.save(minionCard14);
		cardRepository.save(minionCard15);
		cardRepository.save(minionCard16);
		cardRepository.save(minionCard17);
		cardRepository.save(minionCard18);
		cardRepository.save(minionCard19);
		cardRepository.save(minionCard20);
	}

	@Transactional
	private void generatePopCultureSet() {
		// Cartes de Jimmy, theme Comic Relieve (PopCulture/popStar)
		//createMinionCard(name, power, health, speed, manaCost, desc, imagePath)
		MinionCard minionCard35 = createMinionCard("Breaking Cat", 5, 10, 5, 3, "I'm not in the meth business. I'm in the empire business.", "img/cardImg/popcultureSet/BreakingCat.jpg");
		MinionCard minionCard36 = createMinionCard("Princess Leia", 7, 5, 8, 3, "Can't believe I was so foolish to think I could find Luke and bring him home.", "img/cardImg/popcultureSet/Leia.jpg");
		MinionCard minionCard37 = createMinionCard("Catty Gaga", 5, 10, 5, 3, "I was born this way hey!", "img/cardImg/popcultureSet/LadyGaga.jpg");
		MinionCard minionCard38 = createMinionCard("Kitty Perry", 4, 2, 4, 1, "Take me, ta-ta-take me, Wanna be your victim, Ready for abduction, Boy, you're an alien", "img/cardImg/popcultureSet/kitty-perry.jpg");
		MinionCard minionCard39 = createMinionCard("Nikki Miouw", 4, 5, 1, 1, "Hit 'em with the oh now that's just ridiculous, We could've cleared up all these particulars", "img/cardImg/popcultureSet/Nikki_Mioui.jpg");
		MinionCard minionCard40 = createMinionCard("Cat Sparrow", 10, 10, 10, 5, "Why the rum is always gone. ", "img/cardImg/popcultureSet/CatSperow.jpg");
		MinionCard minionCard41 = createMinionCard("Elvis Cat", 22, 9, 4, 6, "Wise men sayOnly fools rush inBut I can't help falling in love with you", "elvis.png");
		MinionCard minionCard42 = createMinionCard("Miouwly Cirus", 9, 11, 5, 4, "I came in like a recking Ball", "img/cardImg/popcultureSet/MileyCirus.jpg");
		MinionCard minionCard43 = createMinionCard("DIE Bunny", 12, 5, 8, 4, "STUPID HUMAIN, A Battery Up My B***, You must All die, DIE DIE DIE DIE", "img/cardImg/popcultureSet/kitty-perry.jpg");
		MinionCard minionCard44 = createMinionCard("Thriller Cat", 12, 11, 12, 6, "Cause this is thriller, Thriller night, And no one’s gonna save you", "img/cardImg/popcultureSet/TrillerCat.jpg");
		cardRepository.save(minionCard35);
		cardRepository.save(minionCard36);
		cardRepository.save(minionCard37);
		cardRepository.save(minionCard38);
		cardRepository.save(minionCard39);
		cardRepository.save(minionCard40);
		cardRepository.save(minionCard41);
		cardRepository.save(minionCard42);
		cardRepository.save(minionCard43);
		cardRepository.save(minionCard44);
		
	}
	
	@Transactional
	private void generateHalloweenSet() {
		// Cartes de Gabriel, theme Halloween
		MinionCard minionCard01 = createMinionCard("Chat Momie", 3, 4, 3, 1, "La malédiction du pharaon",
				"img/cardImg/halloweenSetCards/chat_momie.jpg");
		MinionCard minionCard02 = createMinionCard("Chat-Souris", 3, 3, 4, 1, "Vous avez dit chat-souris?",
				"img/cardImg/halloweenSetCards/chat_souris.jpg");
		MinionCard minionCard03 = createMinionCard("Chat Fantome", 2, 6, 2, 1, "Boo",
				"img/cardImg/halloweenSetCards/chat_fantome.jpg");
		MinionCard minionCard04 = createMinionCard("Chat Noir", 2, 4, 4, 1,
				"Si c'est vendredi 13, bonne chance pour la suite", "img/cardImg/halloweenSetCards/chat_noir.jpg");
		MinionCard minionCard05 = createMinionCard("Jack-O-Chat", 4, 6, 5, 2, "Bonne carte sans l'ombre d'un doute",
				"img/cardImg/halloweenSetCards/jack_o_chat.jpg");
		MinionCard minionCard06 = createMinionCard("Apprenti-Sorcier", 5, 6, 4, 2, "Abra Kadrachat !",
				"img/cardImg/halloweenSetCards/apprenti_sorcier.jpg");
		MinionCard minionCard07 = createMinionCard("Chat Zombie", 6, 4, 5, 2, "OMFG BBQ",
				"img/cardImg/halloweenSetCards/chat_zombie.jpg");
		MinionCard minionCard08 = createMinionCard("FrankenChat", 7, 8, 5, 3,
				"Combien de vies de chats en échange de cette créature ?",
				"img/cardImg/halloweenSetCards/frankenchat.jpg");
		MinionCard minionCard09 = createMinionCard("Chat Poss�d�", 9, 6, 5, 3, "Ehhh boy",
				"img/cardImg/halloweenSetCards/chat_possede.jpg");
		MinionCard minionCard10 = createMinionCard("Chanatique", 6, 8, 6, 3, "Tellement myst�rieux...",
				"img/cardImg/halloweenSetCards/chanatique.jpg");
		
		cardRepository.save(minionCard01);
		cardRepository.save(minionCard02);
		cardRepository.save(minionCard03);
		cardRepository.save(minionCard04);
		cardRepository.save(minionCard05);
		cardRepository.save(minionCard06);
		cardRepository.save(minionCard07);
		cardRepository.save(minionCard08);
		cardRepository.save(minionCard09);
		cardRepository.save(minionCard10);
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

