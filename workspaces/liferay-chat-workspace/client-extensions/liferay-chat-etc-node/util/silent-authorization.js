import axios from 'axios';
import config from './configTreePath.js'
import {headless_admin_userService} from "../headless-wrapper/headless-admin-user.js";
// OAuth2 parameters
const lxcDXPMainDomain = config['com.liferay.lxc.dxp.mainDomain'];
const lxcDXPServerProtocol = config['com.liferay.lxc.dxp.server.protocol'];
const externalReferenceCode = config[
    'liferay.oauth.application.external.reference.codes'
    ].split(',')[0];
import cache from 'memory-cache';
const clientId = config[`${externalReferenceCode}.oauth2.headless.server.client.id`];
const clientSecret =  config[`${externalReferenceCode}.oauth2.headless.server.client.secret`];
const uri =  config[`${externalReferenceCode}.oauth2.token.uri`] || '/o/oauth2/token';

const tokenEndpoint = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}${uri}`
const currentAccountEndPoint = `/o/headless-admin-user/v1.0/my-user-account`;


export function getServerToken(userId)
{

    //expires_in * 1000
    let prom = new Promise((resolve, reject)=>{
        let cacheKey = `server_token`;
        let cachedData = cache.get(cacheKey);
        if (cachedData)
        {
            resolve(cachedData);
        }else
        {
            // Request body
            const requestBody = {
                grant_type: 'client_credentials',
                client_id: clientId,
                client_secret: clientSecret,
            };
            const headers =  {
                'Content-Type': 'application/x-www-form-urlencoded',
            }
            axios
                .post(tokenEndpoint, requestBody,{headers})
                .then((response) => {
                    // Handle the response, which may include an authorization code or access token
                    cache.put(cacheKey,response.data.access_token ,
                        (parseInt(response.data.expires_in) - 5) * 1000);
                    resolve(response.data.access_token);
                })
                .catch((error) => {
                    console.log(error.message);
                    console.error('Error obtaining authorization code:', error.response.data);
                    reject('No valid token!');
                });
        }


    });
    return prom;
}
