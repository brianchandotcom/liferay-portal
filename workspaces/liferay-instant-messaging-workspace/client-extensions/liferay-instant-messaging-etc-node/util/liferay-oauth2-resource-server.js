const { lookupConfig, lxcConfig } = require('@rotty3000/config-node');
const cors = require('cors');
const { verify } = require('jsonwebtoken');
const jwktopem = require('jwk-to-pem');
const fetch = require('fetch');

const { applicationExternalReferenceCodes } = require('./constants.js');
const {getCacheEntry, putCacheEntry} = require("./caching");
const {logError} = require("./log");
const axios = require("axios");

const agentOauthApp = lxcConfig.oauthApplication(
    applicationExternalReferenceCodes.OAUTH_AGENT_EXTERNAL_REFERENCE_CODE
);

const agentUriPath = agentOauthApp.jwksUri();

const domains = lxcConfig.dxpDomains();

const lxcDXPMainDomain = lxcConfig.dxpMainDomain();

const lxcDXPServerProtocol = lxcConfig.dxpProtocol();

const oauthAgentClientId = agentOauthApp.clientId();

const allowList = domains
    ? domains.toString().split(',').map((domain) => `${lxcDXPServerProtocol}://${domain}`)
    : '';

const corsOptions = {
    origin(origin, callback) {
        callback(null, allowList.includes(origin));
    },
};

async function corsWithReady(req, res, next) {
    if (req.originalUrl === lookupConfig('ready.path')) {
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

        process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";

        const jwksResponse = await axios.get(AgentOauth2JWKSURI);

        if (jwksResponse.status === 200) {
            const jwks = await jwksResponse.data;

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
                logError(
                    'JWT token client_id value does not match expected client_id value.'
                );

                res.status(401).send('Invalid authorization');
            }
        }
        else {
            logError(
                'Error fetching JWKS %s %s',
                jwksResponse.status,
                jwksResponse.statusText
            );

            res.status(401).send('Invalid authorization header');
        }
    }
    catch (error) {
        logError('Error validating client JWT token\n%s', error);

        res.status(401).send('Invalid authorization header');
    }
}

async function clientLiferayJWTValidation(bearerToken) {

    const AgentOauth2JWKSURI = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}${agentUriPath}`;

    if (!bearerToken) {
        return false;
    }
    try {
        let jwks = null;

        let jwksPublicKey = null;

        const cachedJWKSPublicKey = getCacheEntry("jwksPublicKey");

        if (cachedJWKSPublicKey){
            jwksPublicKey =  cachedJWKSPublicKey;

        }else {

            process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";

            jwksResponse =  await axios.get(AgentOauth2JWKSURI);

            if (jwksResponse.status === 200){

                jwks = await jwksResponse.data;

                jwksPublicKey = jwktopem(jwks.keys[0]);

                putCacheEntry("jwksPublicKey",jwksPublicKey)

            }else {
                logError(
                    'Error fetching JWKS %s %s',
                    jwksResponse.status,
                    jwksResponse.statusText
                );

                return null;
            }

        }

        const decoded = verify(bearerToken, jwksPublicKey, {
            algorithms: ['RS256'],
            ignoreExpiration: true,
        });

        if (
            decoded.client_id.replaceAll(' ', '') ===
            oauthAgentClientId.replaceAll(' ', '')
        ) {
            return decoded;
        }
        else {

            return null
        }


    }
    catch (error) {
        logError('Error validating client JWT token\n%s', error.message);

       return null
    }
}

async function liferayJWT(req, res, next) {
    if (req.path === lookupConfig('ready.path')) {
        return next();
    }
    else {
        return clientLiferayJWT(req, res, next);
    }
}

module.exports = {
    corsWithReady,
    liferayJWT,
    clientLiferayJWTValidation
};
