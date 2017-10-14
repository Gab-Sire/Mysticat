import React, {Component} from 'react';
import axios from 'axios';

export default class MainMenu extends Component{
	constructor(props){
		super(props);
		this.state={
				playerId: null,
				lookingForGame: false
			}
	}
	render(){
		return (<div id='MainMenu'>
				<div id='menuBox'>
					<h2> Mysticat</h2>
					{(this.state.lookingForGame===false) ? 
							<p><button onClick={this.enterQueue.bind(this)}>Entrer dans la file d'attente</button></p> : 
							<p><button onClick={this.cancelQueue.bind(this)}>Quitter la file d'attente</button></p> }
					
			
					<p><button onClick={this.enterQueue.bind(this)}>Entrer dans la file d'attente</button></p>
					<p><button>Consulter ses decks</button></p>
					<p><button>Regarder une Partie</button></p>
					<p><button onClick={(event)=>{this.cancelQueue(); 
					setTimeout(()=>{
						this.deconnexion();
					}, 1000)
				}
			}
			>Déconnexion</button></p>
				</div>
				<div id="imgMenuPrincipal"></div>
			</div>);
	}
	deconnexion(){
		this.props.disconnectPlayer();
	}
	enterQueue(){
		this.setState({lookingForGame: true})
		this.forceUpdate();
		let data = this.props.playerId;
		console.log("Entering queue");
		axios({
		  method:'post',
		  url:'http://localhost:8089/enterQueue',
		  responseType:'text',
		  headers: {'Access-Control-Allow-Origin': "true"},
		  data: data
		})
		  .then((response)=>{
			  console.log(response);
			  setTimeout(()=>{
				  this.checkIfQueuePopped();
			  }, 1000)
			})
			.catch(error => {
			  console.log('Error fetching and parsing data', error);
			});
	}
	
	
	checkIfQueuePopped(){
		let data = this.props.playerId;
		axios({
		  method:'post',
		  url:'http://localhost:8089/checkIfQueuePopped',
		  responseType:'json',
		  headers: {'Access-Control-Allow-Origin': "true"},
		  data: data
		})
		  .then((response)=>{
			  console.log(this.state.playerId);
			  if(response.data===null && this.state.lookingForGame=== true){
				  setTimeout(()=>{
					  this.checkIfQueuePopped();
				  }, 1000)
			  }
			  else if(this.state.lookingForGame===true){
				  this.props.getQueueForParent(response.data);
			  }
			})
			.catch(error => {
			  console.log('Error fetching and parsing data', error);
			});
	}
	
	cancelQueue(){
		this.setState({lookingForGame: false});
	}
	
	componentWillMount(){
		this.setState({playerId: this.props.playerId});
	}
	
}
