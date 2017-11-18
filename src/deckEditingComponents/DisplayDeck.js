
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
    		collection:null,
        favoriteDeckIndex:null,
    		isEmpty:false,
    		editMode:false
    }
  }
  componentWillMount(){
    this.getFavoriteDeckIndex();
	  this.getDeck();
	  this.getCollection();
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
				  if(0===response.data.cardList.length){
					  this.setState({isEmpty:true})
				  }
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
          this.deconnexion();
		});
	}

  getFavoriteDeckIndex(){
    axios({
			  method:'get',
			  url:'http://'+window.location.hostname+':8089/getFavoriteDeckIndex/'+this.props.playerId,
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"},
				params: {
					userId: this.props.playerId
				  }
			})
			  .then((response)=>{
          console.log("Fave deck:", response.data)
				  this.setState({favoriteDeckIndex: response.data});
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
          this.deconnexion();
		});
  }

  getCollection(){
	  axios({
	        method:'post',
	        url:'http://'+window.location.hostname+':8089/getCollection',
	        responseType:'json',
	        headers: {'Access-Control-Allow-Origin': "true"}
	      })
	        .then((response)=>{
	        	this.setState({collection:response.data});
	        })
	        .catch(error => {
	          console.log('Error fetching and parsing data', error);
            this.deconnexion();
	        });
  }

  render() {
	  if( null !== this.state.deck && null !== this.state.collection){
		  return (
        <div className='MainMenu'>
        	<Beforeunload onBeforeunload={() => {this.deconnexion(); return "Are you sure?"}}/>
          <CardDisplayTable editMode={this.state.isEmpty} goDeckSelection = {this.props.goDeckSelection}
        	deckList={this.state.deck} playerId={this.props.playerId} deckId={this.props.deckId} favorite={this.state.favoriteDeckIndex}
        	appDisplay={this.props.appDisplay} collection={this.state.collection} disconnectPlayer={this.props.disconnectPlayer}/>
        </div>);
	  }else{
		  return (<div>En attente du serveur</div>);
	  }
  }

  deconnexion(){
    this.props.disconnectPlayer();
  }
}
