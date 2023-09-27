/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import cors from 'cors';
import {verify} from 'jsonwebtoken';
import jwktopem from 'jwk-to-pem';
import fetch from 'node-fetch';

import config from './configTreePath.js';
import {logger} from './logger.js';

const domains = config['com.liferay.lxc.dxp.domains'];
const externalReferenceCode = config[
	'liferay.oauth.application.external.reference.codes'
].split(',')[0];
const lxcDXPMainDomain = config['com.liferay.lxc.dxp.mainDomain'];
const lxcDXPServerProtocol = config['com.liferay.lxc.dxp.server.protocol'];

const uriPath =
	config[externalReferenceCode + '.oauth2.jwks.uri'] || '/o/oauth2/jwks';

const oauth2JWKSURI = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}${uriPath}`;

const allowList = domains
	.split(',')
	.map((domain) => `${lxcDXPServerProtocol}://${domain}`);

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

export async function liferayJWT(req, res, next) {
	if (req.path === config.readyPath) {
		return next();
	}

	const authorization = req.headers.authorization;

	if (!authorization) {
		res.status(401).send('No authorization header');

		return;
	}

	const [, bearerToken] = req.headers.authorization.split('Bearer ');

	try {
		const jwksResponse = await fetch(oauth2JWKSURI);

		if (jwksResponse.status === 200) {
			const jwks = await jwksResponse.json();

			const jwksPublicKey = jwktopem(jwks.keys[0]);

			const decoded = verify(bearerToken, jwksPublicKey, {
				algorithms: ['RS256'],
				ignoreExpiration: true, // TODO we need to use refresh token
			});

			const applicationResponse = await fetch(
				`${lxcDXPServerProtocol}://${lxcDXPMainDomain}/o/oauth2/application?externalReferenceCode=${externalReferenceCode}`
			);

			const {client_id} = await applicationResponse.json();

			if (decoded.client_id === client_id) {
				req.jwt = decoded;

				next();
			}
			else {
				logger.log(
					'JWT token client_id value does not match expected client_id value.'
				);

				res.status(401).send('Invalid authorization');
			}
		}
		else {
			logger.error(
				'Error fetching JWKS %s %s',
				jwksResponse.status,
				jwksResponse.statusText
			);

			res.status(401).send('Invalid authorization header');
		}
	}
	catch (error) {
		logger.error('Error validating JWT token\n%s', error);

		res.status(401).send('Invalid authorization header');
	}
}
