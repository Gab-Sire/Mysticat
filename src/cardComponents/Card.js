import React, {Component} from 'react';

export default class Card extends Component{
	constructor(props){
		super(props);
		this.state={faceUp: true, selected:false};
	}
	
	render(){
		let isSelected = "";
		if(true === this.props.active){
			isSelected = "selected";
		}

		if(true === this.props.faceUp){
			return (<div className={"card " + isSelected} title={this.props.description} onClick={this.props.onClick}>
				<div className='cardManaContainer cardManaCost' title="The amount of mana crystals consumed when summoning this minion"><div className=" balancingDetail" >{this.props.manaCost}</div></div>
				<div className="cardName">{this.props.name}</div>
				<div title="The amount of damage this minion deals" className='cardManaContainer cardPower'><div className="balancingDetail">{this.props.initialPower}</div></div>
				<div title="The amount of damage this minion can take" className='cardManaContainer cardHealth'><div className="balancingDetail">{this.props.initialHealth}</div></div>
				<div title="Speed dictates the order in which attacks resolve" className='cardManaContainer cardSpeed'><div className="balancingDetail">{this.props.initialSpeed}</div></div>
			</div>);
		}
		else{
			return (<div className="cardFacedDown"></div>);
		}
	}

	handleClick = () => {
		this.setState(prevState =>({ selected: !this.state.selected }));
	};

	unselect = () => {
		this.setState(prevState => ({ selected: false }));
	}
}
