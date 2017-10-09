import React, { Component } from 'react';

export default class Surrender extends Component {
  constructor(props) {
    super(props);
    this.state = {
      surrender: false
    }
  }

  

  render() {
	  if(this.props.status){
		    return (
		            <div id='GiveUP'>
		            <p>Es-tu sure de vouloir abondonné?</p>
		            <button>Oui</button><button>Non</button>
		            </div>
		        );  
	  }else{
		    return (null);
	  }
  }

}