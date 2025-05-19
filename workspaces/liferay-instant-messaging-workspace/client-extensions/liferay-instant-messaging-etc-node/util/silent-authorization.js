const { lxcConfig } = require('@rotty3000/config-node');
const axios = require('axios');
const cache = require('memory-cache');

const { applicationExternalReferenceCodes } = require('./constants.js');
const {logError} = require("./log");

const serverOauthApp = lxcConfig.oauthApplication(applicationExternalReferenceCodes.OAUTH_SERVER_EXTERNAL_REFERENCE_CODE);

const clientId = serverOauthApp.clientId();

const clientSecret = serverOauthApp.clientSecret();

const lxcDXPMainDomain = lxcConfig.dxpMainDomain();

const lxcDXPServerProtocol = lxcConfig.dxpProtocol();

const uri = serverOauthApp.tokenUri();

const tokenEndpoint = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}${uri}`;

function getServerToken() {

    return new Promise((resolve, reject) => {
        const cacheKey = `server_token`;

        const cachedData = cache.get(cacheKey);

        if (cachedData) {
            resolve(cachedData);
        }
        else {
            try {
                const requestBody = new URLSearchParams({
                    client_id: clientId,
                    client_secret: clientSecret,
                    grant_type: 'client_credentials',
                });

                const headers = {
                    'Content-Type': 'application/x-www-form-urlencoded',
                };

                axios
                    .post(tokenEndpoint, requestBody, { headers })
                    .then((response) => {
                        cache.put(
                            cacheKey,
                            response.data.access_token,
                            (parseInt(response.data.expires_in, 10) - 5) * 1000
                        );
                        const token = response.data.access_token;

                        resolve(token);
                    })
                    .catch((error) => {
                        logError(
                            `Error obtaining authorization code.`,
                            error.response
                        );

                        reject('No valid token!');
                    });
            }
            catch (error) {
                reject('No valid token!');
            }
        }
    });
}

module.exports = {
    getServerToken
};
