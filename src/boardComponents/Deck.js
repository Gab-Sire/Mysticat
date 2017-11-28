
import React, {Component} from 'react';
import CardTile from '../cardComponents/CardTile.js';
import Card from '../cardComponents/Card.js';

export default class Deck extends Component{
	constructor(props){
		super(props);
		this.state={}
	}

	render(){
		if(this.props.size===0){
			return (<div id={this.props.id} className="deck" title="Deck vide.">
				<div className="deckCount">
					{this.props.size}
				</div>
	   			<CardTile />
	   		</div>);
		}
		else{
			return (<div id={this.props.id} className="deck" title="Contient les cartes que vous pouvez piger.">
				<div className="deckCount">
					{this.props.size}
				</div>
	   			<Card faceUp="false" />
	   		</div>);
		}
	}
}
