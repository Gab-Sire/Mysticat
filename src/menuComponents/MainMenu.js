
import React, {Component} from 'react';
import axios from 'axios';
import PopUpQueue from './PopUpQueue.js';
import Beforeunload from 'react-beforeunload';

const TIME_BETWEEN_AXIOS_CALLS = 1000;
export default class MainMenu extends Component{
	constructor(props){
		super(props);
		this.state={
				playerId: null,
				isLookingForGame: false,
				tag:"hidden"
			}
	}

	componentWillMount(){
		this.setState({playerId: this.props.playerId});
	}

	render(){
		return (<div id='MainMenu'>
		<Beforeunload onBeforeunload={() => {this.deconnexion(); return "Are you sure?"}}/>
				<div id='menuBox'>
					<h2 id="titleMenu"> Mysticat</h2>
					<div className='menuContainer'>
						<p><button className='btn btn-lg btn-primary btn-block btn-signin' onClick={this.enterQueue.bind(this)}>Trouver un adversaire</button></p>
						<p><button className='btn btn-lg btn-primary btn-block btn-signin' onClick={this.displayUnderContruction.bind(this)}>Regarder une Partie</button></p>
						<p><button className='btn btn-lg btn-primary btn-block btn-signin' onClick={this.props.goDeckSelection}>Consulter ses decks</button></p>
						<p><button className='btn btn-lg btn-primary btn-block btn-signin' onClick={(event)=>{
								setTimeout(()=>{
									this.deconnexion();
								}, TIME_BETWEEN_AXIOS_CALLS)
							}
						}
						>Déconnexion</button></p>
						<p><button className='btn btn-lg btn-primary btn-block btn-signin' onClick={this.getHardCodedGame.bind(this)}>Get test game</button></p>
						<div className={this.state.tag}>Pas encore disponible</div>
					</div>
				</div>
				<div id="imgMenuPrincipal"></div>
				<PopUpQueue iSQueueingUp={this.state.isLookingForGame} cancelQueue={this.cancelQueue.bind(this)} />
			</div>);
	}

	enterQueue(){
			this.hideUnderContruction();
			this.setState({isLookingForGame: true})
			let data = this.props.playerId;
			axios({
			  method:'post',
			  url:'http://'+window.location.hostname+':8089/enterQueue',
			  responseType:'text',
			  headers: {'Access-Control-Allow-Origin': "true"},
			  data: data
			})
			  .then((response)=>{
				  setTimeout(()=>{
					  this.checkIfQueuePopped();
				  }, TIME_BETWEEN_AXIOS_CALLS)
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
					this.deconnexion();
				});
	}

	checkIfQueuePopped(){
		if(this.state.isLookingForGame===true){
			let data = this.props.playerId;
			axios({
			  method:'post',
			  url:'http://'+window.location.hostname+':8089/checkIfQueuePopped',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"},
			  data: data
			})
			  .then((response)=>{
				  if(response.data===null){
					  setTimeout(()=>{
						  this.checkIfQueuePopped();
					  }, TIME_BETWEEN_AXIOS_CALLS)
				  }
				  else{
						console.log(response.data);
					  this.props.getQueueForParent(response.data);
				  }
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', "checkIfQueuePopped", error);
					this.setState({isLookingForGame: false});
					setTimeout(()=>{
						this.deconnexion();
					}, TIME_BETWEEN_AXIOS_CALLS)
				});
		}
	}

	cancelQueue(){
		this.setState({isLookingForGame: false})
		//Contacter le serveur pour etre removed.
		let data = this.props.playerId;
		axios({
		  method:'post',
		  url:'http://'+window.location.hostname+':8089/cancelQueue',
		  responseType:'json',
		  headers: {'Access-Control-Allow-Origin': "true"},
		  data: data
		}).catch(error => {
			  console.log('Error fetching and parsing data', error);
				this.deconnexion();
			});
	}

	getHardCodedGame(){
		axios({
			  method:'get',
			  url:'http://'+window.location.hostname+':8089/getHardCodedGame',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"}
			})
			  .then((response)=>{
					console.log(response.data);
				  this.props.getQueueForParent(response.data);
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
					this.deconnexion();
				});
	}

	displayUnderContruction(){
		this.setState({tag: "visible"});
	}
	hideUnderContruction(){
		this.setState({tag: "hidden"});
	}

	deconnexion(){
		this.props.disconnectPlayer();
	}
}
