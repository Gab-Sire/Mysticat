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
			return (<div id={this.props.id} className="graveyard" title="Cimetière vide.">
				<div className="graveyardCount">
					{this.props.graveyard.length}
				</div>
					<CardTile />
	   		</div>);
		}
		else{
			let topCardOnGraveyard = this.props.graveyard[this.props.graveyard.length-1];
			return (<div id={this.props.id} className="graveyard" title="Le cimitère contient les minions qui sont morts durant la partie.">
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
