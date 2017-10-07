import React, {Component} from 'react';
import CardTile from '../cardComponents/CardTile.js';
import _ from 'lodash';
import Minion from '../cardComponents/Minion.js';

const MAX_CARDS_FIELD = 7; 

export default class Field extends Component{
	
	constructor(props){
		super(props);
		this.state={
		}
	}
	
	render(){
		let props = this.props;
		return (
		<div id={this.props.id} className="battleField">
			{ _.map(this.props.grid[0], minion=> <Minion {...minion}{...props}/>) }
		</div>
		);
	}
}