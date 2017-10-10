import React, { Component } from 'react';
/**
 * Pop up menu si on a besoin plus tard
 */
export default class Lost extends Component {
  constructor(props) {
    super(props);
    this.state = {
    }
  }

  render() {
	  return (<div>{this.createMessage()}</div>);
  }
  
  createMessage(){
	  if(this.props.status){
		    return (
		            <div id='FullScreenRED'>
			            <div id='blocGiveUp'>
			            	<p>Vous Avez Perdu</p>
			            	<button>Menu Principal</button>
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
  
  NotGivingUP(){
	  this.props.Never();
  }

}