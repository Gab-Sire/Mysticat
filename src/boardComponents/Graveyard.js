import React, {Component} from 'react';
import CardTile from '../cardComponents/CardTile.js';
import Card from '../cardComponents/Card.js';

export default class Graveyard extends Component{
	constructor(props){
		super(props);
		this.state={ isEmpty:true };
	}
	
	render(){
		
		if(this.props.size===0){
			return (<div id={this.props.id} className="graveyard" title="Empty graveyard.">
				<div className="graveyardCount">
					{this.props.size}
				</div>
	   			<CardTile />
	   		</div>);
		}
		else{
			return (<div id={this.props.id} className="graveyard" title="The graveyard contains the minions that have died during the game.">
				<div className="graveyardCount">
					{this.props.size}
				</div>
	   			<Card faceUp="false" />
	   		</div>);
		}
	}
}
