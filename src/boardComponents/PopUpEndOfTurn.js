import React, { Component } from 'react';
/**
 * Pop up menu si on a besoin plus tard
 */
export default class PopUpEndOfTurn extends Component {

  constructor(props) {
    super(props);
    this.state = {}
  }

  render() {
	  return (<div>{this.createPopUp()}</div>);
  }

  createPopUp(){
	  if(this.props.status){
		    return (
		            <div id='FullScreenRED'>
			            <div id='blocGiveUp'>
			            	<p>En attente de l'adversaire ...</p>
			            </div>
		            </div>
		        );
	  }
  }

}
