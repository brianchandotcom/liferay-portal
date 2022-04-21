import React from 'react';
import { Liferay } from '../services/liferay/liferay';
import WebClient from '../services/liferay/webclient';

class NameForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {value: ''};

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);

    this.customRestApp = Liferay.OAuth.getUserAgentApplication('customrestservice');

    this.webClient = new WebClient({
      clientId: this.customRestApp.clientId
    });
  }

  webClient() {
    return this.webClient;
  }

  handleChange(event) {
    this.setState({value: event.target.value});
  }

  handleSubmit(event) {
    this.webClient.fetch(
      'o/headless-admin-user/v1.0/my-user-account'
    ).then(res => {
      this.webClient.fetch(
        `${this.customRestApp.homePageURL}/random/user/${res.id}`, {
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
          New Name:
          <input type="text" value={this.state.value} onChange={this.handleChange} />
        </label>
        <input type="submit" value="Update" />
      </form>
    );
  }
}

export default NameForm;