import React, {Component} from 'react';
import '../styles/admin.css';

export default class AdminDashBoard extends Component{

	constructor(props){
		super(props);
		this.state={

		}
	}

	render(){
		return(
			<div id="containerAdmin">
				<h1>Tableau Administrateur</h1>
				<div id="contentAdmin">
					<p>Bonjour, glorieux administrateur</p><br />
					
				</div>
			</div>
		)
	}
}
