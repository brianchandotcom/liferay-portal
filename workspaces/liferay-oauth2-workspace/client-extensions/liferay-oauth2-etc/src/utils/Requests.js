/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */
export async function getAuthToken({
	clientId,
	clientSecret,
	code,
	grantType,
	redirectUri,
	tokenUrl,
	userName,
	userPassword,
}) {
	const request = {
		client_id: clientId,
		client_secret: clientSecret,
		code,
		grant_type: grantType,
		password: userPassword,
		redirect_uri: redirectUri,
		username: userName,
	};

	let formBody = [];

	for (const property in request) {
		const encodedKey = encodeURIComponent(property);
		const encodedValue = encodeURIComponent(request[property]);

		formBody.push(encodedKey + '=' + encodedValue);
	}

	formBody = formBody.join('&');

	const data = await fetch(tokenUrl, {
		body: formBody,
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded',
		},
		method: 'POST',
	}).then((response) => response.json());

	return data;
}

export async function getUser({token, url}) {
	return fetch(url, {
		headers: {
			'Authorization': 'Bearer ' + token,
			'Content-Type': 'application/json',
		},
		method: 'GET',
	});
}
