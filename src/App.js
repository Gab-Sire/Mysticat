import React, {Component} from 'react';
import _ from 'lodash';
import './App.css';

const player = {
		id: 1,
		name: "ChatOne",
		hand:[
			{
				manaCost : 1,
				power: 1,
				health: 1,
				speed : 1
			},
			{
				manaCost : 2,
				power: 2,
				health: 2,
				speed : 2
			},
			{
				manaCost : 3,
				power: 3,
				health: 3,
				speed : 3
			}
		]
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
				<CreateHand hand={this.state.hand} createCard={this.createTask.bind(this)}/>
				</div>
				);
	}
}



export default App;