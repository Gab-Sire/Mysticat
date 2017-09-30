import React, { Component } from 'react';
import _ from 'lodash';
import './App.css';
import CardListItem from './CardListItem';

export default class CardList extends Component {

  renderItems() {
    const props = _.omit(this.props, 'cards');
    return _.map(this.props.cards, card => <CardListItem {...card} {...props} />);
  }

  render() {
    return (
    		<div id="#selfHand">
    		{this.renderItems()}
    		</div>
    		    );
  }
}