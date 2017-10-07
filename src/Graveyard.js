import React, {Component} from 'react';

export default class Graveyard extends Component{
	constructor(props){
		super(props);
	}
	
	render(){
		return <div id={this.props.id} className="graveyard">
			   		<div className="cardTile">
			   </div>
	</div>	
	}
}
