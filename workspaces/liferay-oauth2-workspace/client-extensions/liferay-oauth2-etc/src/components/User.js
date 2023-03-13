/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

import React from 'react';

import {getUser} from '../utils/Requests';

function User({token}) {
	const [url, setUrl] = React.useState('');
	const [user, setUser] = React.useState({});

	const handleGetUser = async () => {
		await getUser({
			token: token.access_token,
			url,
		})
			.then((response) => response.json())
			.then((data) => setUser(data));
	};

	return (
		<div>
			<h2>Get User</h2>

			<input
				onChange={(event) => setUrl(event.target.value)}
				placeholder="URL for get-current-user"
				style={{width: 500}}
				type="text"
				value={url}
			/>
			(e.g. http://localhost:8080/api/jsonws/user/get-current-user)
			<br />

			<button onClick={handleGetUser}>Get User</button>

			<pre>{JSON.stringify(user, null, 2)}</pre>
		</div>
	);
}

export default User;
