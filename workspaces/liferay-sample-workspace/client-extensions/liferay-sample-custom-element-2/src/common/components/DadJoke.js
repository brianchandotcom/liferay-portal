import React from 'react';

import {Liferay} from '../services/liferay/liferay';

let oAuth2Client;

try {
	oAuth2Client = Liferay.OAuth2Client.FromUserAgentApplication(
		'liferay-sample-etc-spring-boot-oauth-application-user-agent'
	);
}
catch (error) {
	console.error(error);
}

function DadJoke() {
	const [joke, setJoke] = React.useState(null);

	React.useEffect(() => {
		oAuth2Client
			?.fetch('/dad/joke')
			.then((response) => response.text())
			.then((joke) => {
				setJoke(joke);
			});
	}, []);

	if (!joke) {
		return <div>Loading...</div>;
	}

	return <div>{joke}</div>;
}

export default DadJoke;
