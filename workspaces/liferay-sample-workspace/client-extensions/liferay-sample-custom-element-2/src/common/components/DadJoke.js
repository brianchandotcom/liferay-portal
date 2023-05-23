/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React from 'react';

import {Liferay} from '../services/liferay/liferay';

const oAuth2Client = Liferay.OAuth2Client.FromUserAgentApplication(
	'liferay-sample-etc-spring-boot-oauth-application-user-agent'
);

function DadJoke() {
	const [joke, setJoke] = React.useState(null);

	React.useEffect(() => {
		oAuth2Client
			.fetch('/dad/joke')
			.then((response) => response.text())
			.then((joke) => {
				setJoke(joke);
			})
			// eslint-disable-next-line no-console
			.catch((error) => console.log(error));
	}, []);

	if (!joke) {
		return <div>Loading...</div>;
	}

	return <div>{joke}</div>;
}

export default DadJoke;
