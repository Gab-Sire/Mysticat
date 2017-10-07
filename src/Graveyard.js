import React, {Component} from 'react';
import CardTile from './CardTile.js';

export default class Graveyard extends Component{
	constructor(props){
		super(props);
	}
	
	render(){
		return <div id={this.props.id} className="graveyard">
			   		<CardTile />
			   	</div>	
	}
}
