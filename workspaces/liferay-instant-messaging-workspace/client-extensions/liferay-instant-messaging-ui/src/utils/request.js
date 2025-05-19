/* global Liferay */

import axios from 'axios';
import {config} from "./constants";
import {showError} from "./util";

export function getServerUrl() {
	return Liferay.OAuth2Client.FromUserAgentApplication(config.agentOauthAppId)
		.homePageURL;
}

export async function getOAuthToken() {
	const prom = new Promise((resolve, reject) => {
		Liferay.OAuth2Client.FromUserAgentApplication(config.agentOauthAppId)
			._getOrRequestToken()
			.then(
				(token) => {
					resolve(token.access_token);
				},
				(error) => {
					showError('Error', error);

					reject(error);
				}
			)
			.catch((error) => {
				showError('Error', error);

				reject(error);
			});
	});

	return prom;
}

export async function oAuthRequest(config) {
	return request({
		headers: {
			Authorization: `Bearer ${await getOAuthToken()}`,
			...config.headers
		},
		url: `${getServerUrl()}${config.url}`,
		method: config.method,
		data:config.data?config.data:null
	});
}

export function request(config) {
	return new Promise((resolve, reject) => {
		axios
			.request({
				headers: {
					'x-csrf-token': Liferay.authToken,
					'accept-language': Liferay.ThemeDisplay.getLanguageId().split("_")[0],
				},
				method: 'get',
				...config,
			})
			.then((response) => {
				resolve(response.data);
			})
			.catch((error) => {
				reject({error, message: error || ''});
			});
	});
}


export function jsonRequest(url,data){

	var prom = new Promise((resolve, reject)=>{

		try {

			Liferay.Service(
				url, data,
				(result) => {
					resolve(result)
				},
				(error) => {
					reject(null)
				}
			);

		}catch (exp){
			reject(exp.message)
		}
	});

	return prom;

}
