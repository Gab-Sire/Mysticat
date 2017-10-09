import React, {Component} from 'react';

export default class Hero extends Component{
	constructor(props){
		super(props);
		this.state={heroName:"zorroHero"};
	}
	
	render(){
		let heroId = this.props.id;
		let health = this.props.health;
		let mana = this.props.mana;
		this.setState({heroName: this.props.heroName});
		
		if("selfHero" === heroId){
			return(<div id={heroId} className={"hero " + this.state.heroName}>
						<div id="selfHealth" className="heroHealth">
							{health}
						</div>
						<div id="selfMana">
							<div>
								{mana}
							</div>
						</div>
					</div>);
		}
		else if("opponentHero" === heroId){
			return(<div id={heroId} className={"hero " + this.state.heroName}>
						<div id="opponentHealth" className="heroHealth">
							{health}
						</div>
					</div>);
		}
	}
}