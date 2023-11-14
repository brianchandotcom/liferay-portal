import fetch from 'node-fetch';
import config from '../util/configTreePath.js';
import axios from 'axios';
import {getServerToken} from "../util/silent-authorization.js";
import {getOrCreateUserFolder} from "../util/attachment-util.js";

const lxcDXPMainDomain = config['com.liferay.lxc.dxp.mainDomain'] ||
    config['com.liferay.sh.dxp.mainDomain'];
const lxcDXPServerProtocol = config['com.liferay.lxc.dxp.server.protocol'] ||
    config['com.liferay.sh.dxp.server.protocol'];
const oauth2JWKSURI = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}`;
    export function foldertemplatesService(_token) {
        const token = _token;
    function deleteByExternalReferenceCode (externalReferenceCode)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        requestHeaders.append("Content-Type", "application/json");
        var requestOptions = {
          method: 'DELETE',
          headers: requestHeaders,
          redirect: 'follow'
          
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                          .then(response=> {
                if (response.status == 204) {
                    resolve(response.text());
                }else {
                    reject();
                }
            },error=>{
                reject(error)
            }).catch(error => {
                    reject(error)
                });
      })
      return prom;
    }
    

    function getByExternalReferenceCode (externalReferenceCode,fields=null,nestedFields=null,restrictFields=null)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        var requestOptions = {
          method: 'GET',
          headers: requestHeaders,
          redirect: 'follow'
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject( error));
      })
      return prom;
    }
    

    function patchByExternalReferenceCode (externalReferenceCode,FolderTemplate)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        requestHeaders.append("Content-Type", "application/json");
        var requestOptions = {
          method: 'PATCH',
          headers: requestHeaders,
          redirect: 'follow'
          ,body:JSON.stringify(FolderTemplate),
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject( error));
      })
      return prom;
    }
    

    function putByExternalReferenceCode (externalReferenceCode,FolderTemplate)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        requestHeaders.append("Content-Type", "application/json");
        var requestOptions = {
          method: 'PUT',
          headers: requestHeaders,
          redirect: 'follow'
          ,body:JSON.stringify(FolderTemplate),
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject( error));
      })
      return prom;
    }
    

    function postFolderTemplatesPageExportBatch (filter=null,search=null,sort=null,callbackURL=null,contentType=null,fieldNames=null)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        requestHeaders.append("Content-Type", "application/json");
        var requestOptions = {
          method: 'POST',
          headers: requestHeaders,
          redirect: 'follow'
          
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject( error));
      })
      return prom;
    }
    

    function getFolderTemplatesPage (templateID)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        var requestOptions = {
          method: 'GET',
          headers: requestHeaders,
          redirect: 'follow'
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/?filter=templateID eq ${templateID}&page=0`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject( error));
      })
      return prom;
    }
    

    function postFolderTemplate (FolderTemplate)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        requestHeaders.append("Content-Type", "application/json");
        var requestOptions = {
          method: 'POST',
          headers: requestHeaders,
          redirect: 'follow'
          ,body:JSON.stringify(FolderTemplate),
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject( error));
      })
      return prom;
    }
    

    function deleteFolderTemplateBatch (callbackURL=null,requestBodyobject)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        requestHeaders.append("Content-Type", "application/json");
        var requestOptions = {
          method: 'DELETE',
          headers: requestHeaders,
          redirect: 'follow'
          ,body:JSON.stringify(requestBodyobject),
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/batch?callbackURL=${callbackURL}`, requestOptions)
                          .then(response=> {
                if (response.status == 204) {
                    resolve(response.text());
                }else {
                    reject();
                }
            },error=>{
                reject(error)
            }).catch(error => {
                    reject(error)
                });
      })
      return prom;
    }
    

    function postFolderTemplateBatch (callbackURL=null,requestBodyobject)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        requestHeaders.append("Content-Type", "application/json");
        var requestOptions = {
          method: 'POST',
          headers: requestHeaders,
          redirect: 'follow'
          ,body:JSON.stringify(requestBodyobject),
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject( error));
      })
      return prom;
    }
    

    function putFolderTemplateBatch (callbackURL=null,requestBodyobject)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        requestHeaders.append("Content-Type", "application/json");
        var requestOptions = {
          method: 'PUT',
          headers: requestHeaders,
          redirect: 'follow'
          ,body:JSON.stringify(requestBodyobject),
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject( error));
      })
      return prom;
    }
    

    function getOpenAPI (type)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        var requestOptions = {
          method: 'GET',
          headers: requestHeaders,
          redirect: 'follow'
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/openapi.${type}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject( error));
      })
      return prom;
    }
    

    function deleteFolderTemplate (folderTemplateId)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        requestHeaders.append("Content-Type", "application/json");
        var requestOptions = {
          method: 'DELETE',
          headers: requestHeaders,
          redirect: 'follow'
          
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/${folderTemplateId}`, requestOptions)
                          .then(response=> {
                if (response.status == 204) {
                    resolve(response.text());
                }else {
                    reject();
                }
            },error=>{
                reject(error)
            }).catch(error => {
                    reject(error)
                });
      })
      return prom;
    }
    

    function getFolderTemplate (folderTemplateId,fields=null,nestedFields=null,restrictFields=null)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        var requestOptions = {
          method: 'GET',
          headers: requestHeaders,
          redirect: 'follow'
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/${folderTemplateId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject( error));
      })
      return prom;
    }
    

    function patchFolderTemplate (folderTemplateId,FolderTemplate)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        requestHeaders.append("Content-Type", "application/json");
        var requestOptions = {
          method: 'PATCH',
          headers: requestHeaders,
          redirect: 'follow'
          ,body:JSON.stringify(FolderTemplate),
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/${folderTemplateId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject( error));
      })
      return prom;
    }
    

    function putFolderTemplate (folderTemplateId,FolderTemplate)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        requestHeaders.append("Content-Type", "application/json");
        var requestOptions = {
          method: 'PUT',
          headers: requestHeaders,
          redirect: 'follow'
          ,body:JSON.stringify(FolderTemplate),
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/${folderTemplateId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject( error));
      })
      return prom;
    }
    

    function getFolderTemplatePermissionsPage (folderTemplateId,fields=null,nestedFields=null,restrictFields=null,roleNames=null)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        var requestOptions = {
          method: 'GET',
          headers: requestHeaders,
          redirect: 'follow'
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/${folderTemplateId}/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject( error));
      })
      return prom;
    }
    

    function putFolderTemplatePermissionsPage (folderTemplateId)
    {
        var prom = new Promise((resolve, reject)=>{
        var requestHeaders = new Headers();
         requestHeaders.append('Authorization', `Bearer ${token}`);
        requestHeaders.append("Content-Type", "application/json");
        var requestOptions = {
          method: 'PUT',
          headers: requestHeaders,
          redirect: 'follow'
          
        };
        fetch(`${oauth2JWKSURI}/o/c/foldertemplates/${folderTemplateId}/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject( error));
      })
      return prom;
    }
    
    
         return Object.freeze({
            deleteByExternalReferenceCode,getByExternalReferenceCode,patchByExternalReferenceCode,putByExternalReferenceCode,postFolderTemplatesPageExportBatch,getFolderTemplatesPage,postFolderTemplate,deleteFolderTemplateBatch,postFolderTemplateBatch,putFolderTemplateBatch,getOpenAPI,deleteFolderTemplate,getFolderTemplate,patchFolderTemplate,putFolderTemplate,getFolderTemplatePermissionsPage,putFolderTemplatePermissionsPage
        });
    }
