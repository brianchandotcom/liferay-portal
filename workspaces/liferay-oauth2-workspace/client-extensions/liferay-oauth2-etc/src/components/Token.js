/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

import React from 'react';

import {getAuthToken} from '../utils/Requests';

export function Token({grantType, handleToken, redirectUri}) {
	const [clientId, setClientId] = React.useState('');
	const [clientSecret, setClientSecret] = React.useState('');
	const [tokenUrl, setTokenUrl] = React.useState('');
	const [userName, setUserName] = React.useState('');
	const [userPassword, setUserPassword] = React.useState('');

	async function handleGetToken() {
		const urlSearchParams = new URLSearchParams(window.location.search);

		const code = urlSearchParams.get('code');

		const token = await getAuthToken({
			clientId,
			clientSecret,
			code,
			grantType,
			redirectUri,
			tokenUrl,
			userName,
			userPassword,
		});

		handleToken(token);
	}

	return (
		<div className="Token">
			<h2>Get Token</h2>

			<input
				onChange={(event) => setTokenUrl(event.target.value)}
				placeholder="Liferay Token URL"
				style={{width: 500}}
				type="text"
				value={tokenUrl}
			/>
			(e.g. http://localhost:8080/o/oauth2/token)
			<br />

			<input
				onChange={(event) => setClientId(event.target.value)}
				placeholder="Client ID"
				style={{width: 500}}
				type="text"
				value={clientId}
			/>

			<br />

			<input
				onChange={(event) => setClientSecret(event.target.value)}
				placeholder="Client Secret"
				style={{width: 500}}
				type="text"
				value={clientSecret}
			/>

			<br />

			{grantType === 'password' && (
				<div>
					<input
						onChange={(client) => setUserName(client.target.value)}
						placeholder="User Name"
						style={{width: 500}}
						type="text"
						value={userName}
					/>

					<br />

					<input
						onChange={(client) =>
							setUserPassword(client.target.value)
						}
						placeholder="User Password"
						style={{width: 500}}
						type="text"
						value={userPassword}
					/>

					<br />
				</div>
			)}

			<button onClick={handleGetToken}>Get Token</button>
		</div>
	);
}

export default Token;
