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
				<div className='cardDetailContainer cardManaCost' title="The amount of mana crystals consumed when summoning this minion"><div className=" balancingDetail" >{this.props.manaCost}</div></div>
				<img src="../img/cardImg/medievalSetCards/dragonCat.jpg" />
				<div className="cardName">{this.props.name}</div>
				<div title="The amount of damage this minion deals" className='cardDetailContainer cardPower'><div className="balancingDetail">{this.props.initialPower}</div></div>
				<div title="The amount of damage this minion can take" className='cardDetailContainer cardHealth'><div className="balancingDetail">{this.props.initialHealth}</div></div>
				<div title="Speed dictates the order in which attacks resolve" className='cardDetailContainer cardSpeed'><div className="balancingDetail">{this.props.initialSpeed}</div></div>
			</div>);
		}else if(null!==this.props.index){
			return (<div className="cardFacedDown" onClick={this.selectDeck.bind(this)}></div>);
		}
		else{
			return (<div className="cardFacedDown"></div>);
		}
	}

	selectDeck(){
		this.props.deckSelection(this.props.index);
		this.props.appDisplay("displayDeck");
	}
	
	handleClick = () => {
		this.setState(prevState =>({ selected: !this.state.selected }));
	};

	unselect = () => {
		this.setState(prevState => ({ selected: false }));
	}
}
