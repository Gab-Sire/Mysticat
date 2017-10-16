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
			selectedHandCardIndex : null,
			selectedMinionIndex : null,
			cellsOfSummonedMinionsThisTurn : [false, false, false, false, false, false, false]
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
							<div id="opponentField" className="battleField">
								{this.renderField(opponentIndex)}
							</div>
							<Deck id="opponentDeck" size={opponentDeck.length}/>
						</div>
						
						<div id="selfFieldContainer" className="fieldContainer">
							<Graveyard id="selfGraveyard" size={selfGraveyard.length} identity={"self"}/>
							<div id="selfField" className="battleField">
								{this.renderField(selfIndex)}
							</div>
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
						
	handleSelectHandCard = (index) => {
		  if(index === this.state.selectedHandCardIndex){
			  this.setState({ selectedHandCardIndex: null })
			  return;
		  }
		  this.setState({ selectedHandCardIndex: index })
	}
	
	handleSelectMinion = (index, indexPlayer) => {
		let selfIndex = (this.state.gameState.players[0].playerId===this.state.playerId) ? 0 : 1;
		let isEmpty = this.isThisFieldCellEmpty(index);
		let isAssigned = this.isThisFieldCellAssignedToPreviousSummon(index);
		
		if(indexPlayer === selfIndex){
			if(true === isEmpty && false === isAssigned){
				let cellsClone = this.state.cellsOfSummonedMinionsThisTurn.slice();
				cellsClone[index] = true;
				this.setState({ cellsOfSummonedMinionsThisTurn: cellsClone });
			}
		
			if(index === this.state.selectedMinionIndex){
				this.setState({ selectedMinionIndex: null })
			}
			else{
				this.setState({ selectedMinionIndex: index })
			}
		}
		
	}
	
	renderHand = (playerIndex, faceUp) => {
		let selfHand = this.state.gameState.players[playerIndex].hand;
		const props = (this.state.gameState.players[playerIndex].hand);
		
		var handCards = selfHand.map(function(card, index){
		    return (
		     <Card
		       key={"handCard" + index}
		       active={index === this.state.selectedHandCardIndex}
		       onClick={() => this.handleSelectHandCard(index)} faceUp={faceUp} {...card}{...props}/>
		    )
		   }, this)
		return handCards;
	}
	
	renderField = (playerIndex) => {
		let fieldGrid = this.state.gameState.players[playerIndex].field;
		let selfIndex = (this.state.gameState.players[0].playerId===this.state.playerId) ? 0 : 1;
		const props = (this.state.gameState.players[playerIndex].field);
		
		var fieldMinions = fieldGrid.map(function(minion, index){
		    return (
		     <Minion
		       	key={"fieldMinion" + index}
		     	active = {playerIndex === selfIndex ? this.state.cellsOfSummonedMinionsThisTurn[index] : false}
		     	onClick={() => this.handleSelectMinion(index, playerIndex)} {...minion}{...props}/>
		    )
		   }, this)
		return fieldMinions;
	}
	
	componentWillMount(){
		this.setState({gameState: this.props.gameState, isLoaded: true, playerId: this.props.playerId})
	}
	
	surrenderGameConfirmStateChange(){
		let status =this.state.isThinkingToGiveUp;
		this.setState({ isThinkingToGiveUp: !status});

	}
	surrender(){
		let selfIndex = (this.state.gameState.players[0].playerId===this.state.playerId) ? 0 : 1;
		let self = this.state.gameState.players[selfIndex].hero;
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
		
		isThisFieldCellEmpty = (index) => {
			let selfIndex = (this.state.gameState.players[0].playerId===this.state.playerId) ? 0 : 1;
			let self = this.state.gameState.players[selfIndex];
			return (null === self.field[index]);
		}
		
		isThisFieldCellAssignedToPreviousSummon = (index) => {
			return (true === this.state.cellsOfSummonedMinionsThisTurn[index]);
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




