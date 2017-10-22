import React, {Component} from 'react';

export default class Hero extends Component{
	constructor(props){
		super(props);
		this.state={heroName:"zorroHero"};
	}

	componentWillMount(){
		this.setState({heroName: this.props.heroName});
	}

	render(){
		let heroId = this.props.id;
		let health = this.props.health;
		let mana = this.props.mana;

		if("selfHero" === heroId){
			return(<div>
						<div id={heroId} className={"hero " + this.state.heroName}>
							<div id="selfHealth" className="heroHealth">
								{health}
							</div>
						</div>
						<div id="selfMana">
							<div>
								{mana}
							</div>
						</div>
				  </div>);
		}
		else if("opponentHero" === heroId){
			if(true ===this.props.attackerSelected){}
			return(<div id={heroId} className={"hero " + this.state.heroName}>
						<div id="opponentHealth" className="heroHealth">
							{health}
						</div>
					</div>);
		}
	}
}
