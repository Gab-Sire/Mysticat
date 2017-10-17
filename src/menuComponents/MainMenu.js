import React, {Component} from 'react';
import axios from 'axios';
import PopUpQueue from './PopUpQueue.js'

const TIME_BETWEEN_POLLS = 1000;
export default class MainMenu extends Component{
	constructor(props){
		super(props);
		this.state={
				playerId: null,
				lookingForGame: false,
				TAG:"hidden"
			}
	}
	render(){
		return (<div id='MainMenu'>
				<div id='menuBox'>
					<h2> Mysticat</h2>
					<div className='menuContainer'>
						<p><button className='btn btn-lg btn-primary btn-block btn-signin' onClick={this.enterQueue.bind(this)}>Entrer dans la file d'attente</button></p>
						<p><button className='btn btn-lg btn-primary btn-block btn-signin' onClick={this.displayUnderContruction.bind(this)}>Regarder une Partie</button></p>
						<p><button className='btn btn-lg btn-primary btn-block btn-signin' onClick={this.displayUnderContruction.bind(this)}>Consulter ses decks</button></p>
						<p><button className='btn btn-lg btn-primary btn-block btn-signin' onClick={(event)=>{this.cancelQueue(); 
								setTimeout(()=>{
									this.deconnexion();
								}, TIME_BETWEEN_POLLS)
							}
						}
						>Déconnexion</button></p>
						<p><button className='btn btn-lg btn-primary btn-block btn-signin' onClick={this.getHardCodedGame.bind(this)}>Get test game</button></p>
						<div className={this.state.TAG}>Pas encore disponible</div> 
					</div>
				</div>
				<div id="imgMenuPrincipal"></div>
				<PopUpQueue iSQueueingUp={this.state.lookingForGame} cancelQueue={this.cancelQueue.bind(this)} />
			</div>);
	}
	deconnexion(){
		this.hideUnderContruction();
		this.props.disconnectPlayer();
	}
	enterQueue(){
		this.hideUnderContruction();
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
			  }, TIME_BETWEEN_POLLS)
			})
			.catch(error => {
			  console.log('Error fetching and parsing data', error);
			});
	}
	
	getHardCodedGame(){
		axios({
			  method:'get',
			  url:'http://localhost:8089/getHardCodedGame',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"}
			})
			  .then((response)=>{
				  this.props.getQueueForParent(response.data);
				  console.log(response.data);
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
			  if(response.data===null && this.state.lookingForGame=== true){
				  setTimeout(()=>{
					  this.checkIfQueuePopped();
				  }, TIME_BETWEEN_POLLS)
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
		//Contacter le serveur pour etre removed.
		let data = this.props.playerId;
		axios({
		  method:'post',
		  url:'http://localhost:8089/cancelQueue',
		  responseType:'json',
		  headers: {'Access-Control-Allow-Origin': "true"},
		  data: data
		})
		  .then((response)=>{
			  console.log(response);
			})
			.catch(error => {
			  console.log('Error fetching and parsing data', error);
			});
	}
	
	componentWillMount(){
		this.setState({playerId: this.props.playerId});
	}
	displayUnderContruction(){
		this.setState({TAG: "visible"});
	}
	hideUnderContruction(){
		this.setState({TAG: "hidden"});
	}
	
}
