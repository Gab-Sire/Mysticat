
import React, { Component } from 'react';
/**
 * Pop up menu si on a besoin plus tard
 */
export default class SurrenderScreenPopUp extends Component {

  constructor(props) {
    super(props);
    this.state = {
    }
  }

  render() {
	  return (<div>{this.createFrom()}</div>);
  }

  createFrom(){
	  if(this.props.status){
		    return (
		            <div id='FullScreenRED'>
			            <div id='blocGiveUp'>
			            	<p>&Ecirc;tes-vous certain de vouloir abandonner?</p>
			            	<button className="surrenderChoice" onClick={this.surrender.bind(this)}>Oui</button><button className="surrenderChoice" onClick={this.stayInTheGame.bind(this)}>Non</button>
			            </div>
		            </div>
		        );
	  }
  }

  surrender(){
	  this.props.surrender();
  }

  stayInTheGame(){
	  this.props.stayInTheGame();
  }
}
