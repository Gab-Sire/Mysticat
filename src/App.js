import React, {Component} from 'react';
import _ from 'lodash';
import './App.css';

const self = {
		id: 1,
		name: "ChatOne",
		remainingHealth : 30,
		remainingMana : 1,
		selfHand:[
			{
				name: "Chat One",
				manaCost : 1,
				power: 1,
				health: 1,
				speed : 1
			},
			{
				name: "Chat Two",
				manaCost : 2,
				power: 2,
				health: 2,
				speed : 2
			},
			{
				name: "Chat Three",
				manaCost : 3,
				power: 3,
				health: 3,
				speed : 3
			}
		],
		selfDeck:[],
		selfGraveyard:[],
		
}


class App extends Component{
	constructor(props){
		super(props);
		this.state={
				
		}
	}
	render(){
		return(
				<div id="board">
					<div id="opponentHand">
						<div className="card">
							Card back
						</div>
					</div>
					<div id="selfHand">
						<div className="card">
							<div className="cardSpeed">
							Chat One
							Cost: 1
							Power: 10
							Health: 8
							Speed: 10
							</div>
						</div>
					</div>
				</div>
				);
	}
}



export default App;