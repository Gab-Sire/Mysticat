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

		return(
			<div id="deckSelectionContainer">
				<div id="selectionDeckTitle">
					<h2>S&eacute;lection de deck</h2>
				</div>
				<div id="deckSlotsContainer">
					<CardTile />
					<CardTile />
					<CardTile />
				</div>
				<div id="deckSlotsContainerUnderLayer"></div>
				<button id="backToMenu">Retour au menu</button>
			</div>
		)
	}
}
