/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

import React from 'react';

import Token from '../../../components/Token';
import User from '../../../components/User';

function ClientCredentialsFlow() {
	const [token, setToken] = React.useState({});

	return (
		<div>
			<h1>Client Credentials Flow</h1>
			Scope: read your personal user data
			(liferay-json-web-services.everything.read.userprofile)
			<Token grantType="client_credentials" handleToken={setToken} />

			<br />

			{`Authorization token: ${token.access_token}`}

			<User token={token} />
		</div>
	);
}

export default ClientCredentialsFlow;
