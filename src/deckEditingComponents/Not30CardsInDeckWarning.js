import React from 'react';
import { Alert } from 'reactstrap';

class Not30CardsInDeckWarning extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
    };

  }

  render() {
    return (
      <Alert color="warning" isOpen={this.props.visible} toggle={this.props.onDismiss}>
        <strong>ATTENTION!</strong> Un deck doit avoir exactement <strong>{this.props.MAX_CARDS_IN_DECK}</strong> cartes pour être valide.
      </Alert>
    );
  }
}

export default Not30CardsInDeckWarning;
