import fetch from 'node-fetch';
import config from '../util/configTreePath.js';
import axios from 'axios';
import {getServerToken} from "../util/silent-authorization.js";

const lxcDXPMainDomain = config['com.liferay.lxc.dxp.mainDomain'];
const lxcDXPServerProtocol = config['com.liferay.lxc.dxp.server.protocol'];


const oauth2JWKSURI = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}`;

export function chatmessagesService(_token) {
    const token = _token;
    function getChatMessagesPage(aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            let url = `${oauth2JWKSURI}/o/c/chatmessages/?${aggregationTerms?`aggregationTerms=${aggregationTerms}`:''}${fields?`&fields=${fields}`:''}${flatten?`&flatten=${flatten}`:''}${nestedFields?`&nestedFields=${nestedFields}`:''}${restrictFields?`&restrictFields=${restrictFields}`:''}${filter?`&filter=${filter}`:''}${page?`&page=${page}`:''}&${pageSize?`pageSize=${pageSize}`:''}${search?`&search=${search}`:''}${sort?`&sort=${sort}`:''}`;
            fetch(url, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postChatMessage(ChatMessage) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(ChatMessage),
            };
            fetch(`${oauth2JWKSURI}/o/c/chatmessages/`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteChatMessageBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/c/chatmessages/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function postChatMessageBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/c/chatmessages/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putChatMessageBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/c/chatmessages/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteByExternalReferenceCode(externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/c/chatmessages/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getByExternalReferenceCode(externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/c/chatmessages/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchByExternalReferenceCode(externalReferenceCode, ChatMessage) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(ChatMessage),
            };
            fetch(`${oauth2JWKSURI}/o/c/chatmessages/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putByExternalReferenceCode(externalReferenceCode, ChatMessage) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(ChatMessage),
            };
            fetch(`${oauth2JWKSURI}/o/c/chatmessages/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postChatMessagesPageExportBatch(filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/c/chatmessages/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getOpenAPI(type) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/c/chatmessages/openapi.${type}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteChatMessage(chatMessageId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/c/chatmessages/${chatMessageId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getChatMessage(chatMessageId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/c/chatmessages/${chatMessageId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchChatMessage(chatMessageId, ChatMessage) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(ChatMessage),
            };
            fetch(`${oauth2JWKSURI}/o/c/chatmessages/${chatMessageId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putChatMessage(chatMessageId, ChatMessage) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(ChatMessage),
            };
            fetch(`${oauth2JWKSURI}/o/c/chatmessages/${chatMessageId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getChatMessagePermissionsPage(chatMessageId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/c/chatmessages/${chatMessageId}/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putChatMessagePermissionsPage(chatMessageId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/c/chatmessages/${chatMessageId}/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    return Object.freeze({
        getChatMessagesPage,
        postChatMessage,
        deleteChatMessageBatch,
        postChatMessageBatch,
        putChatMessageBatch,
        deleteByExternalReferenceCode,
        getByExternalReferenceCode,
        patchByExternalReferenceCode,
        putByExternalReferenceCode,
        postChatMessagesPageExportBatch,
        getOpenAPI,
        deleteChatMessage,
        getChatMessage,
        patchChatMessage,
        putChatMessage,
        getChatMessagePermissionsPage,
        putChatMessagePermissionsPage
    });
}
