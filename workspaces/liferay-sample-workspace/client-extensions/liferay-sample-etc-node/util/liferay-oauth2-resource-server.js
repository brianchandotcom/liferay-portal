'use strict';

import config from './configTreePath.js';
import cors from 'cors';
import fetch from 'node-fetch';
import {verify} from 'jsonwebtoken';
import jwktopem from 'jwk-to-pem';
import log from './log.js';

const domains = config['com.liferay.lxc.dxp.domains'];
const externalReferenceCode =
	config['liferay.oauth.application.external.reference.codes'].split(',')[0];
const lxcDXPMainDomain = config['com.liferay.lxc.dxp.mainDomain'];

const lxcDXPServerProtocol = config['com.liferay.lxc.dxp.server.protocol'];
const oauth2JWKSURI =
	lxcDXPServerProtocol +
	'://' +
	lxcDXPMainDomain +
	(config[externalReferenceCode + '.oauth2.jwks.uri'] || '/o/oauth2/jwks');
const allowList = domains
	.split(',')
	.map((domain) => lxcDXPServerProtocol + '://' + domain);

const corsOptions = {
	origin: function (origin, callback) {
		if (allowList.includes(origin)) {
			callback(null, true);
		}
		else {
			callback(null, false);
		}
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

	const bearerToken = req.headers.authorization.split('Bearer ')[1];

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
				lxcDXPServerProtocol +
					'://' +
					lxcDXPMainDomain +
					'/o/oauth2/application?externalReferenceCode=' +
					externalReferenceCode
			);
			const {client_id} = await applicationResponse.json();
			if (decoded.client_id === client_id) {
				req.jwt = decoded;
				next();
			}
			else {
				log.error(
					'JWT token client_id value does not match expected client_id value.'
				);
				res.status(401).send('Invalid authorization');
				return;
			}
		}
		else {
			log.error(
				'Error fetching JWKS %s %s',
				jwksResponse.status,
				jwksResponse.statusText
			);
			res.status(401).send('Invalid authorization header');
			return;
		}
	}
	catch (err) {
		log.error('Error validating JWT token\n%s', err);
		res.status(401).send('Invalid authorization header');
		return;
	}
}
