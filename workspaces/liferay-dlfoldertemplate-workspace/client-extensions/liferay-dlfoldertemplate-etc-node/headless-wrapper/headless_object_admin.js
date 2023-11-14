import axios  from 'axios';
import config from '../util/configTreePath.js';
import {URL} from "url";
import {getServerToken} from "../util/silent-authorization.js";


const lxcDXPMainDomain = config['com.liferay.lxc.dxp.mainDomain'] ||
    config['com.liferay.sh.dxp.mainDomain'];
const lxcDXPServerProtocol = config['com.liferay.lxc.dxp.server.protocol'] ||
    config['com.liferay.sh.dxp.server.protocol'];
const oauth2JWKSURI = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}`;
const objectDefinitionsEndPoint = 'o/object-admin/v1.0/object-definitions';
async function postDataToEndpoint(url, postData, bearerToken) {
    let prom = new Promise((resolve, reject) => {
            let config = {
                method: 'post',
                maxBodyLength: Infinity,
                url: url,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${bearerToken}`
                },
                data: JSON.stringify(postData)
            };

            axios.request(config)
                .then((response) => {
                    resolve(response.data);
                })
                .catch((error) => {
                    console.log(`post data error: ${error}`);
                    reject(error);
                });
    });
    return prom;

}
export async function postDefinition(schema, token) {
    const apiUrl = new URL(`${oauth2JWKSURI}/${objectDefinitionsEndPoint}`);
    let result = postDataToEndpoint(apiUrl.href, schema, token);
    return result;
}
export async function deleteProxyObject(externalReferenceCode,bearerToken)
{
    try
    {
        let objectDefinitionId = await getObjectDefinition(externalReferenceCode,bearerToken);
        let deleteResult = await deleteObjectDefinition(objectDefinitionId,bearerToken);
        return true;
    }catch (exp)
    {
        console.error(`Error while deleting Object ${externalReferenceCode}!`);
        return false;
    }

}
export function getObjectDefinition(externalReferenceCode, bearerToken) {
    let prom = new Promise((resolve, reject) => {
        let url = `${oauth2JWKSURI}/${objectDefinitionsEndPoint}/by-external-reference-code/${externalReferenceCode}`;
        let config = {
            method: 'get',
            maxBodyLength: Infinity,
            url: url,
            headers: {
                'Accept': 'application/json',
                'Authorization': bearerToken
            }
        };
        axios.request(config)
            .then(async (response) => {
                let objectDefinitionId = response.data.id;
                resolve(objectDefinitionId);
            })
            .catch((error) => {
                console.log(error.message);
            });
    });
    return prom;
}
function deleteObjectDefinition(objectDefinitionId, bearerToken) {
    let prom = new Promise((resolve, reject) => {
        let url = `${oauth2JWKSURI}/${objectDefinitionsEndPoint}/${objectDefinitionId}`;
        let config = {
            method: 'delete',
            maxBodyLength: Infinity,
            url: url,
            headers: {
                'Accept': 'application/json',
                'Authorization': bearerToken
            }
        };
        axios.request(config)
            .then((response) => {
                resolve(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    });
    return prom;
}
export function publishObjectDefinition(objectDefinitionId, bearerToken) {
    let prom = new Promise((resolve, reject) => {
        let config = {
            method: 'post',
            maxBodyLength: Infinity,
            url: `${oauth2JWKSURI}/o/object-admin/v1.0/object-definitions/${objectDefinitionId}/publish`,
            headers: {
                'Authorization': `Bearer ${bearerToken}`
            }
        };
        axios.request(config)
            .then((response) => {
                resolve(response.data);
            })
            .catch((error) => {
                console.log(error);
                reject(error.message)
            });
    });
    return prom;
}



