import { Liferay } from "./liferay";

const { REACT_APP_LIFERAY_HOST = window.location.origin } = process.env;

const baseFetch = async (url, options = {}) => {
	return fetch(REACT_APP_LIFERAY_HOST + "/" + url, {
		headers: {
			"Content-Type": "application/json",
			"x-csrf-token": Liferay.authToken,
		},
		...options,
	}).then(
		(response) => {
			if (response.ok) {
				const contentType = response.headers.get('content-type');
				if (contentType && contentType.indexOf('application/json') !== -1) {
				  return response.json();
				}
				else {
				  return Promise.resolve(response);
				}
			}
			return Promise.reject(response);
		}
	);
};

export default baseFetch;
