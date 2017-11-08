import React, {Component} from 'react';
import CardTile from '../cardComponents/CardTile.js';
import Card from '../cardComponents/Card.js';

export default class Graveyard extends Component{
	constructor(props){
		super(props);
		this.state={ isEmpty:true };
	}

	render(){
		if(this.props.graveyard.length===0){
			return (<div id={this.props.id} className="graveyard" title="Empty graveyard.">
				<div className="graveyardCount">
					{this.props.size}
				</div>
					<CardTile />
	   		</div>);
		}
		else{
			let topCardOnGraveyard = this.props.graveyard[this.props.graveyard.length-1];
			return (<div id={this.props.id} className="graveyard" title="The graveyard contains the minions that have died during the game.">
				<div className="graveyardCount">
					{this.props.graveyard.length}
				</div>
				<Card
					faceUp={true}
					active= {false}
					name= {topCardOnGraveyard.name}
					initialPower= {topCardOnGraveyard.initialPower}
					initialSpeed= {topCardOnGraveyard.initialSpeed}
					initialHealth= {topCardOnGraveyard.initialHealth}
					manaCost= {topCardOnGraveyard.manaCost}
					imagePath ={topCardOnGraveyard.imagePath} />
	   		</div>);
		}
	}
}
