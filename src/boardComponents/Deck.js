import React, {Component} from 'react';
import CardTile from '../cardComponents/CardTile.js';
import Card from '../cardComponents/Card.js';

export default class Deck extends Component{
	constructor(props){
		super(props);
	}

	render(){
		if(this.props.size===0){
			return (<div id={this.props.id} className="deck" title="Empty deck.">
				<div className="deckCount">
					{this.props.size}
				</div>
	   			<CardTile />
	   		</div>);
		}
		else{
			return (<div id={this.props.id} className="deck" title="Contains the remaining cards you can draw.">
				<div className="deckCount">
					{this.props.size}
				</div>
	   			<Card faceUp="false" />
	   		</div>);
		}
	}
}
