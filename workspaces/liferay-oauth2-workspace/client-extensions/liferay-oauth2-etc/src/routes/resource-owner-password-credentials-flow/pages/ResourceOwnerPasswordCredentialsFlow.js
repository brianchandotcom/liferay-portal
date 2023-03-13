/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

import React from 'react';

import Token from '../../../components/Token';
import User from '../../../components/User';

function ResourceOwnerPasswordCredentialsFlow() {
	const [token, setToken] = React.useState({});

	return (
		<div>
			<h1>Resource Owner Password Credentials Flow</h1>
			Scope: read your personal user data
			(liferay-json-web-services.everything.read.userprofile)
			<Token grantType="password" handleToken={setToken} />

			<br />

			{`Authorization token: ${token.access_token}`}

			<User token={token} />
		</div>
	);
}

export default ResourceOwnerPasswordCredentialsFlow;
