import React, {Component} from 'react';
import '../styles/admin.css';

export default class UserManagement extends Component{

	constructor(props){
		super(props);
		this.state ={
			
		}
	}

	render(){
		return(
			<p>{this.props.index} {this.props.username}</p>
		)
	}
}