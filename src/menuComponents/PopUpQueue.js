import React, { Component } from 'react';
/**
 * Pop up menu si on a besoin plus tard
 */
export default class PopUpQueue extends Component {
  constructor(props) {
    super(props);
    this.state = {
    }
  }

  render() {
	  return (<div>{this.createMessage()}</div>);
  }

  createMessage(){
	  if(true===this.props.iSQueueingUp){
		    return (
		            <div id='FullScreenRED'>
			            <div id='blocGiveUp'>
			            	<p>À la recherche d'un adversaire ... </p>
			            	<button onClick={this.cancelQueue.bind(this)}>Quitter la file d'attente</button>
			            </div>
		            </div>
		        );
	  }
  }

  cancelQueue(){
	  this.props.cancelQueue();
  }
}
