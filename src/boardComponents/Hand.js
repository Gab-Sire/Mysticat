import React, {Component} from 'react';
import _ from 'lodash';
import Minion from '../cardComponents/Minion.js';
import Card from '../cardComponents/Card.js'

export default class Hand extends Component{
	
	constructor(props){
		super(props);
		this.state={
		}
	}
	
	createItems: function(items){
        var output = [];
        for(var i = 0; i < hand.length; i++) output.push(<Card {hand[i] />);
        return output;
    }
	
	render(){
		return 
			<div>_.map(this.props.hand, card=> <Card faceUp={this.props.faceUp} {...card}{...this.props.hand}/></div>
		);
	}
}