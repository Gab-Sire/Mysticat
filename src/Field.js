import React, {Component} from 'react';
import CardTile from './CardTile.js';

const MAX_CARDS_FIELD = 7; 

export default class Field extends Component{
	
	constructor(props){
		super(props);
		this.state={
		}
	}
	
	render(){
		let fieldGrid = [];
		
		this.props.grid[0].forEach((fieldTile)=>{
			if(null == fieldTile){
				fieldGrid.push(<CardTile />);
			}
			else{
				fieldGrid.push(fieldTile);
			}
		})
	
		return (<div id={this.props.id} className="battleField">
					{fieldGrid}
				</div>
		);
	}
}