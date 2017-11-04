import React, {Component} from 'react';

export default class Card extends Component{
	constructor(props){
		super(props);
		this.state={faceUp: true};
	}

	render(){
		let isSelected = "";
		let listedInstance = "";
		let cardName=this.props.name;
		if(null===this.props.cardName){
			cardName = this.props.name;
		}else{
			cardName = this.props.cardName;
		}
		if(true === this.props.active){
			isSelected = "selected";
		}
		if(true === this.props.listed){
			listedInstance="Big ";
		}
		if(true === this.props.faceUp){
			return (<div className={"card"+listedInstance +" "+ isSelected} title={this.props.description} onClick={this.props.onClick}>
				<div className={'cardDetailContainer'+listedInstance+' cardManaCost'} title="The amount of mana crystals consumed when summoning this minion"><div className=" balancingDetail" >{this.props.manaCost}</div></div>
				<img src= {'/' + this.props.imagePath}  />
				<div className="cardName">{cardName}</div>
				<div title="The amount of damage this minion deals" className={'cardPower'+listedInstance}><div className='placingAttribut'>{this.props.initialPower}</div></div>
				<div title="The amount of damage this minion can take" className={'cardHealth'+listedInstance}><div className="placingAttribut">{this.props.initialHealth}</div></div>
				<div title="Speed dictates the order in which attacks resolve" className={'cardSpeed'+listedInstance}><div className="placingAttribut">{this.props.initialSpeed}</div></div>
			</div>);
		}else if(null!==this.props.index){
			if(true===this.props.isUserDeck){
					return (
							<div className="cardFacedDown" title={this.props.deck.name} onClick={this.selectDeck.bind(this)}>
							</div>
						);
			}
			else{
				return (<div className="cardFacedDown"></div>);
			}
		}
		else{
			return (<div className="cardFacedDown"></div>);
		}
	}

	selectDeck(){
		this.props.deckSelection(this.props.index);
		this.props.appDisplay("displayDeck");
	}

	unselect = () => {
		this.setState(prevState => ({ selected: false }));
	}
}
