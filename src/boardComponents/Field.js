import React, {Component} from 'react';
import _ from 'lodash';
import Minion from '../cardComponents/Minion.js';

export default class Field extends Component{
	
	constructor(props){
		super(props);
		this.state={
		}
	}
	
	render(){
		let props = this.props;
		let keyIndex = 20;
		
		var fieldMinions = this.props.grid[0].map(function(minion, index){
		    return (
		     <Minion
		       key={"fieldMinion" + index}
		       active={index === this.state.activeIndex}
		       onClick={() => this.handleClick(index)} {...minion}{...props}/>
		    )
		   }, this)
		
		return (
		<div id={this.props.id} className="battleField">
			 {fieldMinions}
		</div>
		);
	}
}