import React, {Component} from 'react';
import CardTile from './CardTile.js';

const MAX_CARDS_FIELD = 7; 

export default class Card extends Component{
	
	constructor(props){
		super(props);
		this.state={
		}
	}
	
	render(){
		let gridCardTiles = [];
		for (var i=0; i < MAX_CARDS_FIELD; i++) {
			gridCardTiles.push(<CardTile />);
		}
		
		return (<div id={this.props.id} className="battleField">
					{gridCardTiles}
				</div>
		);
	}
}