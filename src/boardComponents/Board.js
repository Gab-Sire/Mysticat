import React, {Component} from 'react';
import axios from 'axios';
import '../styles/app.css';
import _ from 'lodash';
import Card from '../cardComponents/Card.js';
import Field from './Field.js';
import Graveyard from './Graveyard.js';
import Deck from './Deck.js';
import Hero from './Hero.js';

export default class Board extends Component{
	constructor(props){
		super(props);
		this.state ={
				gameState : {
					currentMana: 0,
					players: [
						{
							hero: {
								health:30
							},
							hand : [
								{
									name: "test",
									initialPower: "test",
									manaCost: "test",
									initialHealth: "test",
									initialSpeed: "test",
									description: "test"
								}
							], 
							field : [
								null, 
								null, 
								null, 
								null, 
								null, 
								null, 
								null
							],
							graveyard:[],
							deck: []
						},
						{
							hero:{
								health:30
							},
							hand : [
								{
									name: "test",
									initialPower: "test",
									manaCost: "test",
									initialHealth: "test",
									initialSpeed: "test",
									description: "test"

								}
							],
							field : [
								null, 
								null, 
								null, 
								null, 
								null, 
								null, 
								null
							],
							graveyard:[],
							deck:[]
						}
					]
			}
			};
	}
	
	render(){
		let self = this.state.gameState.players[0];
		let opponent = this.state.gameState.players[1];
		let selfHealth = self.hero.health;
		let opponentHealth = opponent.hero.health;
		let selfMana = self.remainingMana;
			return(
				<div id="container">
					<div id="board">
						<div id="opponentHand" className="hand">
							{this.renderHand(0, true)}
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
							{this.renderHand(0, false)}
						</div>
						<button id="buttonEndTurn" onClick={this.updateGameState.bind(this)}>Fin de tour</button>
					</div>
				</div>
			);
	}
	
	renderHand(playerIndex, faceUp){
		let selfHand = this.state.gameState.players[playerIndex].hand;
		const props = (this.state.gameState.players[playerIndex].hand);
		return _.map(selfHand, card=> <Card faceUp={faceUp} {...card}{...props}/>);
		
	}

	componentWillMount(){
		this.getInitialGameInstance();
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
