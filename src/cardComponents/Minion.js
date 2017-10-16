import React, {Component} from 'react';

export default class Card extends Component{
	constructor(props){
		super(props);
		this.state={isEmpty: true};
	}
	
	render(){
		let isEmpty = this.state.isEmpty;
		isEmpty = (this.props.name!=null) ? true : false;
		if(isEmpty){
			return (<div className="cardTile minion" title={this.props.description} onClick={this.props.onClick}>
				<div className="cardName">{this.props.name}</div>
				<div title="The amount of damage this minion deals" className="cardPower">Power: {this.props.power}</div>
				<div title="The amount of damage this minion can take" className="cardHealth">Health: {this.props.health}</div>
				<div title="Speed dictates the order in which attacks resolve" className="cardSpeed">Speed: {this.props.speed}</div>
			</div>);
		}
		else{
			return (<div className="cardTile" onClick={this.props.onClick}></div>);
		}	
	}
}