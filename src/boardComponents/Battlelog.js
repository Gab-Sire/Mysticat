import React, { Component } from 'react';
/**
 * Battlelog de la game
 */
export default class Battelog extends Component {


  constructor(props) {
    super(props);
    this.state = {}
  }

  render() {
    return (<div>{this.createBoxBattlelog()}</div>);
  }

  createBoxBattlelog(){
    if(this.props.status){
    	let props = this.props.logs;
    	const listLogs = props.map((logs) =>
    	  <p>{logs}</p>
    	);
        return (
        		<div id="battlelogZone">
        			{listLogs}
        		</div>
            );
    }
  }

}
