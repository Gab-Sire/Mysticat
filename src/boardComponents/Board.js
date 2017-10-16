import React, {Component} from 'react';
import axios from 'axios';
import '../styles/app.css';
import _ from 'lodash';
import Card from '../cardComponents/Card.js';
import Field from './Field.js';
import Graveyard from './Graveyard.js';
import Deck from './Deck.js';
import Hero from './Hero.js';
import SurrenderScreenPopUp from './SurrenderScreenPopUp.js';
import EndGameScreen from './EndGameScreen.js';
import LoadingScreen from '../menuComponents/LoadingScreen';

export default class Board extends Component{
	constructor(props){
		super(props);
		this.state ={
			playerId: null,
			isThinkingToGiveUp: false,
			hasLostGame : false,
			actionList : [],
			activeIndex : 20
			};
	}
	 
	
	render(){
		let selfIndex = (this.state.gameState.players[0].playerId===this.state.playerId) ? 0 : 1;
		let opponentIndex = (selfIndex===0) ? 1 : 0;
		let self = this.state.gameState.players[selfIndex];
		let opponent = this.state.gameState.players[opponentIndex];
		let selfHealth = self.hero.health;
		let opponentHealth = opponent.hero.health;
		let selfMana = self.remainingMana;
		let selfName = self.name;
		let opponentName = opponent.name;
		let selfDeck = self.deck;
		let opponentDeck = opponent.deck;
		let selfField = self.field;
		let opponentField = opponent.field;
		let selfGraveyard = self.graveyard;
		let opponentGraveyard = opponent.graveyard;
			return(
				<div id="container">
					<div id="board">
						<div id="opponentHand" className="hand">
							{this.renderHand(opponentIndex, true)}
						</div>
						<div id="opponentHandUnderLayer"></div>
						<Hero id="opponentHero" health={opponentHealth} heroName="wizardHero"/>
							
						<div id="fieldContainer" className="fieldContainer">
							<Graveyard id="opponentGraveyard" size={opponentGraveyard.length} identity={"opponent"}/>
							<Field id="opponentField" grid={[opponentField]} />
							<Deck id="opponentDeck" size={opponentDeck.length}/>
						</div>
						
						<div id="selfFieldContainer" className="fieldContainer">
							<Graveyard id="selfGraveyard" size={selfGraveyard.length} identity={"self"}/>
							<Field id="selfField" grid={[selfField]} />
							<Deck id="selfDeck" size={selfDeck.length}/>
						</div>
						<div id="selfFieldUnderLayer"></div>
							
						<Hero id="selfHero" health={selfHealth} mana={selfMana} heroName="zorroHero"/>
							
						<div id="selfHand" className="hand">
							{this.renderHand(selfIndex, false)}
						</div>
						<button id="buttonEndTurn" onClick={this.sendActions.bind(this)}>Fin de tour</button>
						
						<SurrenderScreenPopUp status={this.state.isThinkingToGiveUp} enough={this.surrender.bind(this)} never={this.surrenderGameConfirmStateChange.bind(this)} />
						<EndGameScreen status={this.state.hasLostGame} goingMainMenu={this.goingMainMenu.bind(this)}/>

						<div id="menuGame"><p>Menu</p>
							<p id="listeMenuHidden"><button id="ButtonSurrender" onClick={this.surrenderGameConfirmStateChange.bind(this)}>J'abandonne</button></p>
						</div>
						
						<div id="opponentUserName"><p>{opponentName}</p></div>
						<div id="selfUserName"><p>{selfName}</p></div>
					</div>
				</div>
			);
	}
						
	handleClick = (index) => {
		  this.setState({ activeIndex: index })
	}
	
	renderHand = (playerIndex, faceUp) => {
		let selfHand = this.state.gameState.players[playerIndex].hand;
		const props = (this.state.gameState.players[playerIndex].hand);
		
		var handCards = selfHand.map(function(card, index){
		    return (
		     <Card
		       key={"handCard" + index}
		       active={index === this.state.activeIndex}
		       onClick={() => this.handleClick(index)} faceUp={faceUp} {...card}{...props}/>
		    )
		   }, this)
		return handCards;
	       
	}

	componentWillMount(){
		this.setState({gameState: this.props.gameState, isLoaded: true, playerId: this.props.playerId})
	}
	
	getInitialGameInstance(){
		axios({
			  method:'get',
			  url:'http://localhost:8089/getHardCodedGame',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"}
			})
			  .then((response)=>{
				  this.setState({gameState: response.data});
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
	
	surrenderGameConfirmStateChange(){
		let status =this.state.isThinkingToGiveUp;
		this.setState({ isThinkingToGiveUp: !status});

	}
	surrender(){
		let self = this.state.gameState.players[0].hero;
		self.health = 0;
		this.surrenderGameConfirmStateChange();
		this.loseGame();
	}
	
	loseGame(){
		this.setState({ hasLostGame: true});
	}

	goingMainMenu(){
		this.props.endGame();
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
						  this.setState({gameState: response.data});
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
		//TODO EQ1-89 et EQ1-91
		isThisFieldCellAvailableForSummon(){
			return null;
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




