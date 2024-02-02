/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import cors from 'cors';
import {verify} from 'jsonwebtoken';
import jwktopem from 'jwk-to-pem';
import fetch from 'node-fetch';

import {getConfigByKey, getOAuthConfigByKey} from './config-util.js';
import config from './configTreePath.js';
import {
	applicationERCs,
	environmentConfigKeys,
	oauthAgentConfigKeys,
} from './constants.js';

const agentUriPath = getOAuthConfigByKey(
	applicationERCs.OAUTH_AGENT_ERC,
	oauthAgentConfigKeys._OAUTH2_JWKS_URI
);

const domains = getConfigByKey(
	environmentConfigKeys.COM_LIFERAY_LXC_DXP_DOMAINS
);

const lxcDXPMainDomain = getConfigByKey(
	environmentConfigKeys.COM_LIFERAY_LXC_DXP_MAIN_DOMAIN
);

const lxcDXPServerProtocol = getConfigByKey(
	environmentConfigKeys.COM_LIFERAY_LXC_DXP_SERVER_PROTOCOL
);

const oauthAgentClientId = getOAuthConfigByKey(
	applicationERCs.OAUTH_AGENT_ERC,
	oauthAgentConfigKeys._OAUTH2_USER_AGENT_CLIENT_ID
);

const allowList = domains
	? domains.split(',').map((domain) => `${lxcDXPServerProtocol}://${domain}`)
	: '';

const corsOptions = {
	origin(origin, callback) {
		callback(null, allowList.includes(origin));
	},
};

export async function corsWithReady(req, res, next) {
	if (req.originalUrl === config.readyPath) {
		return next();
	}

	return cors(corsOptions)(req, res, next);
}

async function clientLiferayJWT(req, res, next) {
	const authorization = req.headers.authorization;

	const AgentOauth2JWKSURI = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}${agentUriPath}`;

	if (!authorization) {
		res.status(401).send('No authorization header Agent');

		return;
	}
	const [, bearerToken] = req.headers.authorization.split('Bearer ');
	try {
		const jwksResponse = await fetch(AgentOauth2JWKSURI);

		if (jwksResponse.status === 200) {
			const jwks = await jwksResponse.json();

			const jwksPublicKey = jwktopem(jwks.keys[0]);

			const decoded = verify(bearerToken, jwksPublicKey, {
				algorithms: ['RS256'],
				ignoreExpiration: true,
			});
			if (
				decoded.client_id.replaceAll(' ', '') ===
				oauthAgentClientId.replaceAll(' ', '')
			) {
				req.token = bearerToken;
				req.jwt = decoded;
				next();
			}
			else {
				// eslint-disable-next-line no-console
				console.error(
					'JWT token client_id value does not match expected client_id value.'
				);

				res.status(401).send('Invalid authorization');
			}
		}
		else {
			// eslint-disable-next-line no-console
			console.error(
				'Error fetching JWKS %s %s',
				jwksResponse.status,
				jwksResponse.statusText
			);

			res.status(401).send('Invalid authorization header');
		}
	}
	catch (error) {
		// eslint-disable-next-line no-console
		console.error('Error validating client JWT token\n%s', error);

		res.status(401).send('Invalid authorization header');
	}
}

export async function liferayJWT(req, res, next) {
	if (req.path === config.readyPath) {
		return next();
	}
	else {
		return clientLiferayJWT(req, res, next);
	}
}
