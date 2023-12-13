/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
function suggestServerUrl() {
	const OAuthApp = getLiferay().OAuth2Client.FromUserAgentApplication(
		getConfig().agentOauthAppId
	).homePageURL;

	return OAuthApp;
}
function getConfig() {
	const config = {
		'agentOauthAppId':
			'liferay-dlfoldertemplate-oauth-application-user-agent',
		'apiHost': 'http://localhost:8080',
		'external': false,
		'folder.generate.service.url': 'jobs/create/folder/direct',
		'templateInfoApi': 'o/c/t4t14foldertemplates',
		'templateNodeApi': 'o/c/t4t14foldertemplatenodes',
	};

	return config;
}
async function getOAuthToken() {
	const prom = new Promise((resolve, reject) => {
		getLiferay()
			.OAuth2Client.FromUserAgentApplication(getConfig().agentOauthAppId)
			._getOrRequestToken()
			.then(
				(token) => {
					resolve(token.access_token);
				},
				(error) => {
					ShowError(error);
					reject(error);
				}
			)
			.catch((error) => {
				ShowError(error);
				reject(error);
			});
	});

	return prom;
}
function getHostUrl() {
	return getConfig().external ? getConfig().apiHost : '';
}
function ShowSuccess(message) {
	getLiferay().Util.openToast({title: message, type: 'success'});
}
function ShowError(message) {
	getLiferay().Util.openToast({title: message, type: 'danger'});
}
function getDefaultSpriteMap() {
	if (getLiferay()) {
		return getLiferay().Icons.spritemap;
	}
	else {
		return '';
	}
}
function getLiferay() {
	return window.Liferay || null;
}

export const ApplicationUtil = {
	ShowError,
	ShowSuccess,
	getConfig,
	getDefaultSpriteMap,
	getHostUrl,
	getLiferay,
	getOAuthToken,
	suggestServerUrl,
};
