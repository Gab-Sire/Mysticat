import React, {Component} from 'react';
import './styles/menu.css';
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
			emptyDeckSlots.push(<CardTile key={"emptyDeckSlot" + i} />);
		}
		
		return(
			<div id="deckSelectionContainer">
				<div id="selectionDeckTitle">
					<h2>S&eacute;lection de deck</h2>
				</div>
				<div id="deckSlotsContainer">
					{this.props.deckList.map((deck, index) => <Card key={"deckSlot" + index} index={index} deckSelection={this.props.deckSelection} appDisplay={this.props.appDisplay} faceUp={false} {...deck} />)}
					{emptyDeckSlots}
				</div>
				<div id="deckSlotsContainerUnderLayer"></div>
				<button id="backToMenu" onClick={this.goBackToMenu.bind(this)}>Retour au menu</button>
			</div>
		)
	}
					
	goBackToMenu(){
		this.props.appDisplay("menu");
	}				
}
