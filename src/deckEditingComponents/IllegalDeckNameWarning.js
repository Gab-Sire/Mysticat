import React from 'react';
import { Alert } from 'reactstrap';

class IllegalDeckNameWarning extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
    };

  }

  render() {
    return (
      <Alert color="warning" isOpen={this.props.visible} toggle={this.props.onDismiss}>
        <strong>ATTENTION!</strong> Un nom de deck doit comprendre entre <strong>{this.props.MIN_DECK_NAME_LENGTH}</strong>
         et <strong>{this.props.MAX_DECK_NAME_LENGTH}</strong> caractères pour être valide.
      </Alert>
    );
  }
}

export default IllegalDeckNameWarning;
