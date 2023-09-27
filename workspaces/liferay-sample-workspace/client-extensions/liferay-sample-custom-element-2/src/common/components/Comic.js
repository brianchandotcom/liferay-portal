/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {Liferay} from '../services/liferay/liferay.js';

let oAuth2Client;

try {
	oAuth2Client = Liferay.OAuth2Client.FromUserAgentApplication(
		'liferay-sample-etc-node-oauth-application-user-agent'
	);
}
catch (error) {
	console.error(error);
}

function Comic() {
	const [comicData, setComicData] = React.useState(null);

	React.useEffect(() => {
		oAuth2Client?.fetch('/comic').then((comic) => {
			setComicData({
				alt: comic.alt,
				img: comic.img,
				title: comic.safe_title,
			});
		});
	}, []);

	return !comicData ? (
		<div>Loading...</div>
	) : (
		<div>
			<h2>{comicData.title}</h2>

			<p>
				<img alt={comicData.alt} src={comicData.img} />
			</p>
		</div>
	);
}

export default Comic;
