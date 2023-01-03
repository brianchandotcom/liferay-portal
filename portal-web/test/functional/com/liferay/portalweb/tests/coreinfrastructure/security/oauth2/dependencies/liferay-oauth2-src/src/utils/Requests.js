export const getAuthToken = async (props) => {
	const request = {
		client_id: props.clientId,
		client_secret: props.clientSecret,
		code: props.code,
		grant_type: 'authorization_code',
		redirect_uri: 'http://localhost:3000/liferay-oauth2',
	};

	let formBody = [];
	for (let property in request) {
		var encodedKey = encodeURIComponent(property);
		var encodedValue = encodeURIComponent(request[property]);
		formBody.push(encodedKey + '=' + encodedValue);
	}
	formBody = formBody.join('&');

	const data = await fetch(props.tokenUrl, {
		body: formBody,
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded',
		},
		method: 'POST',
	}).then(response => response.json());

	return data;
};

export const getUser = async (props) => {
	return fetch(
		props.url,
		{
			headers: {
				'Authorization': 'Bearer ' + props.token,
				'Content-Type': 'application/json',
			},
			method: 'GET',
		}
	);
};
