import React, {Component} from 'react';

export default class Card extends Component{
	constructor(props){
		super(props);
		this.state={faceUp: true};
	}



	render(){
		let isSelected = "";
		let listedInstance = "";
		let bigStat = "";
		let cardName=this.props.name;
		let isSummoned = "";
		let isFavorite = "";

		if(null!==this.props.listed && true===this.props.listed ){
			cardName = this.props.cardName;
		}
		if(true === this.props.active){
			isSelected = "selected";
		}
		if(true === this.props.listed){
			listedInstance+="Big ";
			bigStat = "cardStatBig";
		}
		if(true===this.props.isFromCollection){
			listedInstance+=" cardInCollection";
		}
		if(null!== this.props.isSummoned && true===this.props.isSummoned){
			isSummoned= " invoquer ";
		}
		if(true===this.props.isFavorite){
			isFavorite = " favoriteDeck";
		}
		if(true === this.props.faceUp){
			return (<div className={"card"+listedInstance +" "+ isSelected+" "+" "+isSummoned} title={this.props.description} onClick={this.props.onClick}>
				<div className={'cardDetailContainer'+listedInstance+' cardManaCost'} title="The amount of mana crystals consumed when summoning this minion"><div className=" balancingDetail" >{this.props.manaCost}</div></div>
				<img src= {'/' + this.props.imagePath}  className="cardArt" alt="card art" />
				<div className={"cardName"+listedInstance}>{cardName}</div>
				<div title="Les points de dégât que ce minion inflige" className={'cardPower'+listedInstance+" "+bigStat}><div className='placingAttribut'>{this.props.initialPower}</div></div>
				<div title="Les points de dégâts que ce minion peut prendre" className={'cardHealth'+listedInstance+" "+bigStat}><div className="placingAttribut">{this.props.initialHealth}</div></div>
				<div title="La vitesse dicte l'ordre dans lequel les attaques se produisent" className={'cardSpeed'+listedInstance+" "+bigStat}><div className="placingAttribut">{this.props.initialSpeed}</div></div>
			</div>);
		}else if(null!==this.props.index){
			if(true===this.props.isUserDeck){
					return (
							<div className={"cardFacedDown"+isFavorite} title={this.props.deck.name} onClick={this.selectDeck.bind(this)}>
							<p className="displayDeckAjouter">Afficher le Deck</p>
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
