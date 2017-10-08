import React, {Component} from 'react';
import axios from 'axios';
import '../styles/app.css';
import _ from 'lodash';
import Card from '../cardComponents/Card.js';
import Minion from '../cardComponents/Minion.js';
import CardTile from '../cardComponents/CardTile.js';
import Field from './Field.js';
import Graveyard from './Graveyard.js';
import Deck from './Deck.js';
import Hero from './Hero.js';

export default class Board extends Component{
	constructor(props){
		super(props);
		this.state={}
	}
	
	render(){
		
		this.state.gameState = this.props.gameState;
		let self = this.state.gameState.players[0];
		let opponent = this.state.gameState.players[1];
		let selfHealth = self.hero.health;
		let opponentHealth = opponent.hero.health;
		let selfMana = self.remainingMana;
			return(
				<div id="container">
					<div id="board">
						<div id="opponentHand" className="hand">
						<div className="cardFacedDown"></div>
						</div>
						
						<Hero id="opponentHero" health={opponentHealth} heroName="wizardHero"/>
							
						<div id="opponentFieldContainer" className="fieldContainer">
							<Graveyard id="opponentGraveyard" size={opponent.graveyard.length} identity={"opponent"}/>
							<Field id="opponentField" grid={[opponent.field]} />
							<Deck id="opponentDeck" size={opponent.deck.length}/>
						</div>
						
						<div id="selfFieldContainer" className="fieldContainer">
							<Graveyard id="selfGraveyard" size={self.graveyard.length} identity={"self"}/>
							<Field id="selfField" grid={[self.field]} />
							<Deck id="selfDeck" size={self.deck.length}/>
						</div>
							
						<Hero id="selfHero" health={selfHealth} mana={selfMana} heroName="warriorHero"/>
							
						<div id="selfHand" className="hand">
							{this.renderSelfHand()}
						</div>
						<button onClick={this.updateGameState.bind(this)}>End turn</button>
					</div>
				</div>
			);
	}
	
	renderSelfHand(){
		let selfHand = this.state.gameState.players[0].hand;
		const props = (this.state.gameState.players[0].hand);
		return _.map(selfHand, card=> <Card {...card}{...props}/>);
		
	}
	
	//// Fonction qui fetch un game state fait par Spring avec valeurs par
// defaut g et In it ia
lGameInstance(){
		axios({
			  method:'get',
			  url:'http://localhost:8089/getHardCodedGame',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"}
			})
			  .then((response)=>{
				  this.setState({gameState: response.data});
				  this.forceUpdate();
				  console.log(response.data);
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				});
	}
	
	updateGameState(){
		const data = this.state.gameState;
		axios({
			  method:'post',
			  url:'http://localhost:8089/updateGame',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"},
			  data: data
			})
			  .then((response)=>{
				  this.setState({gameState: response.data});
				  this.forceUpdate();
				  console.log(response.data);
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				});
		}
	}
