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
        return (
        		<div id="battlelogZone">
        			<p>Battlelog</p>
        		</div>	
            ); 
    } 
  } 
 
}