import React, { Component } from 'react';
/**
 * Pop up menu si on a besoin plus tard
 */
export default class EndGameScreen extends Component {
  constructor(props) {
    super(props);
    this.state = {
    }
  }

  render() {
	  return (<div>{this.createMessage()}</div>);
  }

  createMessage(){
	  if(this.props.status === 1){
		    return (
		            <div id='FullScreenRED'>
			            <div id='blocGiveUp'>
			            	<p>Vous Avez Perdu</p>
			            	<button onClick={this.backToMainMenu.bind(this)}>Menu Principal</button>
			            </div>
		            </div>
		        );
	  } else if(this.props.status === 0){
		  return (
		            <div id='FullScreenGREEN'>
			            <div id='blocGagne'>
			            	<p>Vous Avez Gagné!!!</p>
			            	<button onClick={this.backToMainMenu.bind(this)}>Menu Principal</button>
			            </div>
		            </div>
		        );
	  } else if(this.props.status === -1){
		  return (
		            <div id='FullScreenORANGE'>
			            <div id='blocPartieNulle'>
			            	<p>Partie Nulle</p>
			            	<button onClick={this.backToMainMenu.bind(this)}>Menu Principal</button>
			            </div>
		            </div>
		        );
	  } else {
		  return (null);
	  }
  }

  backToMainMenu(){
	  this.props.backToMainMenu();
  }
}
