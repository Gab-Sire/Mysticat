
import React, {Component} from 'react';

export default class Card extends Component{
	constructor(props){
		super(props);
		this.state={isEmpty: true};
	}

	render(){
		let isEmpty = this.state.isEmpty;
		let isSelected = "";
		let classNameExtra = "cardTile minion";

		if(true === this.props.active){
			isSelected = " fiedlCellSelected";
		}
		if(true === this.props.belongsToSelf && true ===this.props.activeAttacker){
			classNameExtra = "cardTile minion attacker card";
		}else if(true===this.props.attackerSelected && false === this.props.belongsToSelf){
			classNameExtra = "cardTile minion target";
		}

		isEmpty = (this.props.name!=null) ? true : false;
		if(isEmpty){
			return (<div className={(true === this.props.active)? classNameExtra+isSelected :classNameExtra} title={this.props.description} onClick={(true === this.props.active)?null:this.props.onClick}>
				<img src={'/' + this.props.cardReference.imagePath} className="cardArt" alt="card art" ></img>
				<div className="cardName">{this.props.name}</div>

				<div title="The amount of damage this minion deals" className="cardPower"><div className="placingAttribut">{this.props.power}</div></div>
				<div title="The amount of damage this minion can take" className=" cardHealth"><div className="placingAttribut">{this.props.health}</div></div>
				<div title="Speed dictates the order in which attacks resolve" className=" cardSpeed"><div className="placingAttribut">{this.props.speed}</div></div>
			</div>);
		}else{
			if(true === this.props.active){
				return (<div className={"cardTile " + isSelected} ></div>);
			}else{
				return (<div className={"cardTile " + isSelected} onClick={this.props.onClick}></div>);
			}

		}
	}
}

