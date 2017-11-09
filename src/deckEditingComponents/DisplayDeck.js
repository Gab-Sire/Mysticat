import React, { Component } from 'react';
import axios from 'axios';
import '../styles/app.css';
import CardDisplayTable from './CardDisplayTable.js';
import Beforeunload from 'react-beforeunload';

export default class DisplayDeck extends Component {

  constructor(props) {
    super(props);
    this.state = {
    		deck:null,
    		isEmpty:false
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
				  if(0===this.state.deck.deckList.length){
					  this.setState({isEmpty:true})
				  }
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
		});
	}

  render() {
	  if(this.state.deck !== null){
		  return (
        <div className='MainMenu'>
        	<Beforeunload onBeforeunload={() => {this.deconnexion(); return "Are you sure?"}}/>
          <CardDisplayTable editMode={this.state.isEmpty} goDeckSelection = {this.props.goDeckSelection} deckList={this.state.deck} playerId={this.props.playerId} deckId={this.props.deckId} appDisplay={this.props.appDisplay}/>
        </div>);
	  }else{
		  return (<div>En attente du serveur</div>);
	  }
  }

  deconnexion(){
    this.props.disconnectPlayer();
  }
}
