import React from 'react';
import {Liferay} from '../services/liferay/liferay';

function DadJoke() {
	const [joke, setJoke] = React.useState(null);
	const oAuth2Client = Liferay.OAuth2Client.FromUserAgentApplication(
		'liferay-sample-etc-spring-boot-oauth-application-user-agent'
	);

	React.useEffect(() => {
		const request = oAuth2Client
			.fetch('/dad/joke')
			.then((response) => response.text())
			.then((joke) => {
				setJoke(joke);
			});

		return () => console.log(request);
		request.cancel();
	}, []);

	return !joke ? <div>Loading...</div> : <div>{joke}</div>;
}

export default DadJoke;
