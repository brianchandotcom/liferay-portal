import React from 'react';

class Comic extends React.Component {
	constructor(props) {
		super(props);

		this.oAuth2Client = props.oAuth2Client;
		this.state = {
			alt: '',
			img: '',
			title: '',
		};
	}

	componentDidMount() {
		if (this.oAuth2Client) {
			this._request = this.oAuth2Client.fetch('/comic').then((comic) => {
				this._request = null;
				this.setState({
					alt: comic.alt,
					img: comic.img,
					title: comic.safe_title,
				});
			});
		}
	}

	componentWillUnmount() {
		if (this._request) {
			this._request.cancel();
		}
	}

	render() {
		if (this.state === null) {
			return <div>Loading...</div>;
		}
		else {
			return (
				<div>
					<h2>{this.state.title}</h2>
					<p>
						<img alt={this.state.alt} src={this.state.img} />
					</p>
				</div>
			);
		}
	}
}

export default Comic;
