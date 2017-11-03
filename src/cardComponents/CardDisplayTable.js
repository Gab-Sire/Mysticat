import React, { Component } from 'react';
import axios from 'axios';
import '../styles/app.css';
import Card from '../cardComponents/Card.js';


const TIME_BETWEEN_AXIOS_CALLS = 1000;

export default class CardDisplayTable extends Component {

  constructor(props) {
    super(props);
    this.state = {
    		
    }
  }
  
  
  render() {
	return (<table><tr><Card /></tr></table>);  
	  
  }
}