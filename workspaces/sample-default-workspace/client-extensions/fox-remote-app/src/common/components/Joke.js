import React from 'react';

class Joke extends React.Component {
	constructor(props) {
		super(props);
		this.oAuth2Client = props.oAuth2Client;
		this.state = {
			joke: ""
		};
	}

	componentDidMount() {
		this._jokeRequest = this.oAuth2Client.fetch(
			'/dadjoke'
		).then(res => res.text()
		).then(text => {
			this._jokeRequest = null;
			this.setState({joke: text});
		});
	}

	componentWillUnmount() {
		if (this._jokeRequest) {
			this._jokeRequest.cancel();
		}
	}

	render() {
		if (this.state.joke === null) {
			return <div>Loading...</div>
		} else {
			return <div>{this.state.joke}</div>
		}
	}
}

export default Joke;
