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
			<div>
			<div id="deckSelection">
				<div id="deckSlotsContainer">
					<CardTile />
					<CardTile />
					<CardTile />
				</div>
			</div>
			</div>
		)
	}
}
