/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import cors from 'cors';
import {verify} from 'jsonwebtoken';
import jwktopem from 'jwk-to-pem';
import cache from 'memory-cache';
import fetch from 'node-fetch';

import {headless_admin_userService} from '../headless-wrapper/headless-admin-user.js';
import config from './configTreePath.js';
import {logger} from './logger.js';
import {getServerToken} from './silent-authorization.js';

const projectId = config['project-id'];
const domains =
	config['com.liferay.lxc.dxp.domains'] ||
	config['com.liferay.sh.dxp.domains'];
const [agentExternalReferenceCode] = config[
	'liferay.oauth.application.external.reference.codes'
].split(',');
const lxcDXPMainDomain =
	config['com.liferay.lxc.dxp.mainDomain'] ||
	config['com.liferay.sh.dxp.mainDomain'];
const lxcDXPServerProtocol =
	config['com.liferay.lxc.dxp.server.protocol'] ||
	config['com.liferay.sh.dxp.server.protocol'];
const agentUriPath =
	config[agentExternalReferenceCode + '.oauth2.jwks.uri'] || '/o/oauth2/jwks';
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
				ignoreExpiration: true, // TODO we need to use refresh token
			});
			const client_id =
				config[
					`${projectId}-oauth-application-user-agent.oauth2.user.agent.client.id`
				] ||
				config[
					`${projectId}-oauth-application-user-agent.sh.oauth2.headless.agent.client.id`
				];
			if (
				decoded.client_id.replaceAll(' ', '') ===
				client_id.replaceAll(' ', '')
			) {
				req.token = bearerToken;
				req.jwt = decoded;
				const username = req.jwt.username;
				req.user = await getUserProfile(username);
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
		logger.error('Error validating client JWT token\n%s', error);

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

async function getUserProfile(username) {
	const cacheKey = `user_profile_${username}`;
	const profile = cache.get(cacheKey);
	if (profile) {
		return profile;
	}
	else {
		const token = await getServerToken('System');
		const usersService = new headless_admin_userService(`Bearer ${token}`);
		const filter = `filter=emailAddress eq '${username}'`;
		const userProfile = await usersService.getUserAccountsPage(filter);
		cache.put(cacheKey, userProfile.items[0], 60 * 1000 * 4);

		return userProfile.items[0];
	}
}
