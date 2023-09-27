/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function postSpringBootData({data, redirect, urlPath}) {
	let oAuth2Client;

	try {
		oAuth2Client = Liferay.OAuth2Client.FromUserAgentApplication(
			'liferay-jethr0-etc-spring-boot-oauth-application-user-agent'
		);
	}
	catch (error) {
		console.error(error);
	}

	oAuth2Client
		?.fetch(urlPath, {body: JSON.stringify(data), method: 'POST'})
		.then((response) => response.text())
		.then((data) => {
			if (redirect !== null) {
				redirect(data);
			}
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}

export default postSpringBootData;
