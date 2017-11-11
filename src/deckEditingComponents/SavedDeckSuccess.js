import React from 'react';
import { Alert } from 'reactstrap';

class SavedDeckSuccess extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
    };

  }

  render() {
    return (
      <Alert color="success" isOpen={this.props.visible} toggle={this.props.onDismiss}>
        Deck sauvegardé avec succès!
      </Alert>
    );
  }
}

export default SavedDeckSuccess;
