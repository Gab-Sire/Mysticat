import React, { Component } from 'react';
/**
 * Pop up menu si on a besoin plus tard
 */
export default class Surrender extends Component {
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
		            <div id='GiveUP'>
			            <div id='blocGiveUp'>
			            	<p>Es-tu sur de vouloir abandonner?</p>
			            	<button onClick={this.IGiveUp.bind(this)}>Oui</button><button>Non</button>
			            </div>
		            </div>
		        );  
	  }else{
		    return (null);
	  }
  }
  IGiveUp(){
	  this.props.enough();
  }
  INotGivingUP(){
	  
  }

}