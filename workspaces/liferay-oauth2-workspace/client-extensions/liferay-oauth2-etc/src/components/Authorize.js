/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

import React from 'react';

function Authorize() {
	const [authUrl, setAuthUrl] = React.useState('');
	const [clientId, setClientId] = React.useState('');

	function handleAuthorize(event) {
		event.preventDefault();

		try {
			window.location.replace(
				authUrl + '?response_type=code&client_id=' + clientId
			);
		}
		catch (error) {
			throw new Error(error);
		}
	}

	return (
		<div>
			<h2>Authorize</h2>

			<input
				onChange={(event) => setAuthUrl(event.target.value)}
				placeholder="Liferay Authorize URL"
				style={{width: 500}}
				type="text"
				value={authUrl}
			/>
			(e.g. http://localhost:8080/o/oauth2/authorize)
			<br />

			<input
				onChange={(event) => setClientId(event.target.value)}
				placeholder="Client ID"
				style={{width: 500}}
				type="text"
				value={clientId}
			/>

			<br />

			<form onSubmit={handleAuthorize}>
				<button type="onSubmit">Authorize</button>
			</form>

			<br />

			{'Authorization code:'}

			{new URLSearchParams(window.location.search).get('code')}
		</div>
	);
}

export default Authorize;
