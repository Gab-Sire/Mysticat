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
	  if(true===this.props.status){
		    return (
		            <div id='FullScreenRED'>
			            <div id='blocGiveUp'>
			            	<p>Vous Avez Perdu</p>
			            	<button onClick={this.goingMainMenu.bind(this)}>Menu Principal</button>
			            </div>
		            </div>
		        );  
	  }else{
		    return (null);
	  }
  }
  
  goingMainMenu(){
	  this.props.goingMainMenu();
  }

}