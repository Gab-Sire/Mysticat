import React, {Component} from 'react';
import './styles/app.css';
import Login from "./Login.js";
import Signup from "./Signup.js";
import Card from './cardComponents/Card.js';
import CardTile from './cardComponents/CardTile.js';

export default class DeckSelection extends Component{
	
	constructor(props){
		super(props);
		this.state={
			
		};
	}
	
	render(){
		let numberDecks = this.props.deckList.length;
		let slotsRemaining = 3 - numberDecks;
		let emptyDeckSlots = [];
		for(var i = 0; i < slotsRemaining; i++){
			emptyDeckSlots.push(<CardTile />);
		}
		
		return(
			<div id="deckSelectionContainer">
				<div id="selectionDeckTitle">
					<h2>S&eacute;lection de deck</h2>
				</div>
				<div id="deckSlotsContainer">
				{this.props.deckList.map((deck, index) => (<Card key={"deckSlot" + index} faceUp={false} />))}
				{emptyDeckSlots}
				</div>
				<div id="deckSlotsContainerUnderLayer"></div>
				<button id="backToMenu">Retour au menu</button>
			</div>
		)
	}
}
