import React, {Component} from 'react';

export default class LoadingScreen extends Component{
	constructor(props){
		super(props);
		this.state={}
	}
	
	render(){
		return(
				<div id="loadingScreenContainer">
					<div id="loadingScreen"></div>
					<h3 id='loading'>Loading ...</h3>
				</div>
		);
	}
}