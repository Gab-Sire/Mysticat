import React, {Component} from 'react';
import axios from 'axios';
import '../styles/app.css';
import _ from 'lodash';
import Card from '../cardComponents/Card.js';
import Field from './Field.js';
import Graveyard from './Graveyard.js';
import Deck from './Deck.js';
import Hero from './Hero.js';
import Minion from '../cardComponents/Minion.js';
import Hand from './Hand.js';
import SurrenderScreenPopUp from './SurrenderScreenPopUp.js';
import EndGameScreen from './EndGameScreen.js';
import LoadingScreen from '../menuComponents/LoadingScreen';

let players;
let selfIndex;
let self;
let opponent;
let opponentIndex;

let minionToBeSummonedIndex;
let cardIndex;
let minionIndexRetrieved;
let cardIndexRetrieved;

export default class Board extends Component{
	constructor(props){
		super(props);
		this.state ={
			playerId: null,
			isThinkingToGiveUp: false,
			hasLostGame : false,
			actionList : [],
			cellsOfSummonedMinionsThisTurn : [false, false, false, false, false, false, false]
		};		
	}
	
	componentWillMount(){
		this.setState({gameState: this.props.gameState, isLoaded: true, playerId: this.props.playerId})
		
		//initializing players main attributes from the gamestate
		players = this.props.gameState.players;
		selfIndex = (players[0].playerId === this.props.playerId) ? 0 : 1;
		opponentIndex = (selfIndex === 0) ? 1 : 0;
		self = players[selfIndex];
		opponent = players[opponentIndex];
		
		minionIndexRetrieved = false;
		cardIndexRetrieved = false;
	}
	
	retrieveCardSelectedIndex = (selectedIndex) => {
		cardIndex = selectedIndex;
		cardIndexRetrieved = true;
	}
	
	retrieveMinionSelectedIndex = (selectedIndex) =>{
		minionToBeSummonedIndex = selectedIndex;
		minionIndexRetrieved = true;
		
		if(true === minionIndexRetrieved && true === cardIndexRetrieved){
			this.addSummonAction();
		}
	}
	
	addSummonAction = () => {
		console.log(cardIndex);
	
		let actions = this.state.actionList;
		actions.push({ 	playerIndex : selfIndex, 
						indexOfCardInHand : cardIndex,
						fieldCellWhereTheMinionIsBeingSummoned : minionToBeSummonedIndex
					});
		this.setState({ actionList: actions })
		
		minionIndexRetrieved = false;	cardIndexRetrieved = false;
	}
	
	/* methods to surrender/quit the game */
	
	surrenderGameConfirmStateChange(){
		let status = this.state.isThinkingToGiveUp;
		this.setState({ isThinkingToGiveUp: !status });
	}
	
	surrender(){
		self.health = 0;
		this.surrenderGameConfirmStateChange();
		this.loseGame();
	}
	
	loseGame(){
		this.setState({ hasLostGame: true });
	}

	goingMainMenu(){
		this.props.endGame();
	}
	
	render(){
		return(
			<div id="container">
				<div id="board">
					<div id="opponentHand" className="hand">
						<Hand players={players} playerIndex={opponentIndex} faceUp={false} />
					</div>
					<div id="opponentHandUnderLayer"></div>
					<Hero id="opponentHero" health={opponent.hero.health} heroName="wizardHero"/>
							
					<div id="fieldContainer" className="fieldContainer">
						<Graveyard id="opponentGraveyard" size={opponent.graveyard.length} identity={"opponent"}/>
						<div id="opponentField" className="battleField">
							<Field players={players} playerIndex={opponentIndex} active={false} />
						</div>
						<Deck id="opponentDeck" size={opponent.deck.length}/>
					</div>
						
					<div id="selfFieldContainer" className="fieldContainer">
						<Graveyard id="selfGraveyard" size={self.graveyard.length} identity={"self"}/>
						<div id="selfField" className="battleField">
							<Field players={players} playerIndex={selfIndex} active={true} self={self} callBackSelectedMinion={this.retrieveMinionSelectedIndex}
							 cellsOfSummonedMinionsThisTurn={this.state.cellsOfSummonedMinionsThisTurn} />
						</div>
						<Deck id="selfDeck" size={self.deck.length}/>
					</div>
					<div id="selfFieldUnderLayer"></div>
							
					<Hero id="selfHero" health={self.hero.health} mana={self.remainingMana} heroName="zorroHero"/>
							
					<div id="selfHand" className="hand">
						<Hand players={players} playerIndex={selfIndex} faceUp={true} callBackSelectedCardIndex={this.retrieveCardSelectedIndex}
						cellsOfSummonedMinionsThisTurn ={this.state.cellsOfSummonedMinionsThisTurn} />
					</div>
					<button id="buttonEndTurn" onClick={this.sendActions.bind(this)}>Fin de tour</button>
						
					<SurrenderScreenPopUp status={this.state.isThinkingToGiveUp} enough={this.surrender.bind(this)} never={this.surrenderGameConfirmStateChange.bind(this)} />
					<EndGameScreen status={this.state.hasLostGame} goingMainMenu={this.goingMainMenu.bind(this)}/>

					<div id="menuGame"><p>Menu</p>
						<p id="listeMenuHidden"><button id="ButtonSurrender" onClick={this.surrenderGameConfirmStateChange.bind(this)}>J'abandonne</button></p>
					</div>
						
					<div id="opponentUserName"><p>{opponent.name}</p></div>
					<div id="selfUserName"><p>{self.name}</p></div>
				</div>
			</div>
		);
	}
					
	getInitialGameInstance(){
		axios({
			  method:'get',
			  url:'http://localhost:8089/getHardCodedGame',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"}
			})
			  .then((response)=>{
				  this.setState({gameState: response.data,
					  });
				  this.setState({isLoaded: true});
				  this.forceUpdate();
				  console.log(response.data);
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				  setTimeout(()=>{
					  this.getInitialGameInstance();
				  }, 5000)
		});
	}

	//TODO EQ1-96
	sendActions(){
		const data = {
					gameId: this.state.gameState.gameId,
					playerActions: this.state.actionList,
					playerId: this.state.playerId
			};
		console.log("Sending actions: ", data);
		axios({
			  method:'post',
			  url:'http://localhost:8089/sendActions',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"},
			  data: data
			})
			  .then((response)=>{
				  this.checkIfGameUpdated();
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				});
	}
	
		checkIfGameUpdated(){
			const data = this.state.playerId;
			axios({
				  method:'post',
				  url:'http://localhost:8089/checkIfGameUpdated',
				  responseType:'json',
				  headers: {'Access-Control-Allow-Origin': "true"},
				  data: data
				})
				  .then((response)=>{
					  console.log(response.data);
					  if(response.data!==null){
						  	this.setState({gameState: response.data,
								  actionList : [],
								  cellsOfSummonedMinionsThisTurn: [false, false, false, false, false, false, false]
						  	});
						  	
							players = this.state.gameState.players;
							self = this.state.gameState.players[selfIndex];
							opponent = this.state.gameState.players[opponentIndex];
							this.forceUpdate();
					  }
					  else{
						  setTimeout(()=>{
							  this.checkIfGameUpdated();
						  }, 1000)
					  }
					})
					.catch(error => {
					  console.log('Error fetching and parsing data', error);
					});
		}
		
	//TODO EQ1-93 et EQ1-95
	doesThisFieldCellHaveAMinionThatCanAttack(){
		return null;
	}
		
	//TODO EQ1-88
	addSummonActionToList(playerIndex, indexOfCardPlayed, indexOfFieldCell){
		return null;
	}
		
	//TODO EQ1-94
	addAttackActionToList(playerIndex, attackingMinionIndex, targetIndex, speed){
		return null;
	}		
}