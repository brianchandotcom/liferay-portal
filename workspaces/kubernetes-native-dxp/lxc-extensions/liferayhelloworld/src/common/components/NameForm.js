import React from 'react';
import api from '../services/liferay/api';

class NameForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {value: ''};

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);

    this.webClient = props.webClient;
  }

  handleChange(event) {
    this.setState({value: event.target.value});
  }

  handleSubmit(event) {
    api(
      'o/headless-admin-user/v1.0/my-user-account'
    ).then(res => {
      this.webClient.fetch(
        `random/user/${res.id}`, {
          method: 'PUT',
          body: this.state.value
        }
      ).then(() => {
        window.location.reload(false);
      });
    });
    event.preventDefault();
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <label>
          Set Given Name:
          <input type="text" value={this.state.value} onChange={this.handleChange} />
        </label>
        <input type="submit" value="Set" />
      </form>
    );
  }
}

export default NameForm;