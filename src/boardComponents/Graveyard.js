import React, {Component} from 'react';
import CardTile from '../cardComponents/CardTile.js';
import Card from '../cardComponents/Card.js';

export default class Graveyard extends Component{
	constructor(props){
		super(props);
		this.state={ isEmpty:true};
	}
	
	render(){
		if(this.props.isEmpty != null && this.props.isEmpty != undefined){
			this.state.isEmpty = this.props.isEmpty;
		}
		
		if(true == this.state.isEmpty){
			return (<div id={this.props.id} className="graveyard">
	   			<CardTile />
	   		</div>);
		}
		else{
			return (<div id={this.props.id} className="graveyard">
	   			<Card faceUp="false" />
	   		</div>);
		}
	}
}
