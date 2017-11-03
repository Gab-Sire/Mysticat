import React, { Component } from 'react';
import axios from 'axios';
import './styles/app.css';
import CardDisplayTable from './cardComponents/CardDisplayTable.js';
import Beforeunload from 'react-beforeunload';

const TIME_BETWEEN_AXIOS_CALLS = 1000;

export default class DisplayDeck extends Component {

  constructor(props) {
    super(props);
    this.state = {
    		deck:null,
    		modeMode:false
    }
  }
  componentWillMount(){
	  this.getDeck();
  }
  getDeck(){
		axios({
			  method:'get',
			  url:'http://'+window.location.hostname+':8089/selectOneDeck/'+this.props.playerId+'/'+this.props.deckId,
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"},
				params: {
					userId: this.props.playerId,
					deckId:this.props.deckId
				  }
			})
			  .then((response)=>{
				  this.setState({deck: response.data});

				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				  setTimeout(()=>{
					  //this.getDeck();
				  }, TIME_BETWEEN_AXIOS_CALLS)
		});
	}

  render() {
	  if(this.state.deck !== null){
		  return (
        <div className='MainMenu'>
        	<Beforeunload onBeforeunload={() => {this.deconnexion(); return "Are you sure?"}}/>
          <CardDisplayTable deckList={this.state.deck} playerId={this.props.playerId} deckId={this.props.deckId} appDisplay={this.props.appDisplay}/>
        </div>);
	  }else{
		  return (<div>En attente du serveur</div>);
	  }

  }
}
