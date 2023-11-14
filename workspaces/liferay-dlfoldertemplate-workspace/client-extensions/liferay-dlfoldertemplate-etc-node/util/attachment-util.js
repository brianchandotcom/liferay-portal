import {headless_deliveryService} from "../headless-wrapper/headless-delivery.js";
import {getServerToken} from "./silent-authorization.js";
import config from '../util/configTreePath.js';
import axios from 'axios';

const lxcDXPMainDomain = config['com.liferay.lxc.dxp.mainDomain'] ||
    config['com.liferay.sh.dxp.mainDomain'];
const lxcDXPServerProtocol = config['com.liferay.lxc.dxp.server.protocol'] ||
    config['com.liferay.sh.dxp.server.protocol'];
const oauth2JWKSURI = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}`;

const viewByOwner = "Owner";
const viewByAnyone = "Anyone";
const viewOption = viewByAnyone;

const chat_attachments_folder_external_reference_code =
    config['jobs.attachments.folder.external.reference.code'];

export async function getOrCreateMainFolder(globalSiteId,userId) {

    let prom = new Promise(async (resolve, reject) => {
        let token = await getServerToken(userId);
        let delivery_srv = new headless_deliveryService(token);
        let folder = await delivery_srv
            .getSiteDocumentsFolderByExternalReferenceCode
            (globalSiteId, chat_attachments_folder_external_reference_code);
        if (folder && folder.id) {
            console.log(`Main folder is available!, Folder ID: ${folder.id}`);
            resolve(folder.id);
        } else {
            console.log(`main folder is not available!`);
            let folderObject = {
                "description": "Liferay Chat Attachments Folder",
                "externalReferenceCode": chat_attachments_folder_external_reference_code,
                "name": chat_attachments_folder_external_reference_code,
                "parentDocumentFolderId": 0,
                "viewableBy": viewOption
            }
            let folder = await delivery_srv.postSiteDocumentFolder(globalSiteId, folderObject);
            console.log(`Main folder has been created!, Folder ID: ${folder.id}`);
            resolve(folder.id);
        }
    });
    return prom;

}
export async function getOrCreateUserFolder(globalSiteId, uploaderUserId) {

    let prom = new Promise(async (resolve, reject) => {
        let folderKey = `user_id_${uploaderUserId}`;
        let token = await getServerToken(uploaderUserId);
        let delivery_srv = new headless_deliveryService(token);
        let folder = await delivery_srv
            .getSiteDocumentsFolderByExternalReferenceCode
            (globalSiteId,folderKey);
        if (folder && folder.id)
        {
            console.log(`User folder is available!, Folder ID: ${folder.id}`);
            resolve(folder.id);
        }else
        {
            let mainFolderId = await getOrCreateMainFolder(globalSiteId,uploaderUserId);
            console.log(`main folder is not available!`);
            let folderObject = {
                "description": "Liferay Chat Attachments Folder Uploaded by User "+uploaderUserId,
                "externalReferenceCode": folderKey,
                "name": `User_${uploaderUserId}_Attachments`,
                "viewableBy": viewOption
            }
            let folder = await delivery_srv.postDocumentFolderDocumentFolder(mainFolderId ,folderObject);
            console.log(`User folder has been created!, Folder ID: ${folder.id}`);
            resolve(folder.id);
        }
    });
    return prom;
}
export async function uploadFileWithMetadata(userId,siteId,formData)
{
    let prom = new Promise(async (resolve, reject) => {
        try
        {
            let token = await getServerToken(userId);
            let folder = await getOrCreateUserFolder(siteId,userId)
            let config = {
                method: 'post',
                maxBodyLength: Infinity,
                url: `${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${folder}/documents`,
                headers: {
                    'Authorization': `Bearer ${token}`,
                    ...formData.getHeaders()
                },
                data: formData
            };
            axios.request(config)
                .then((response) => {
                    resolve(response.data);
                })
                .catch((error) => {
                    console.log(`Error while uploading file to server '${error.message}'`);
                    console.log('------------Start of request details:------------')
                    console.log(`url: ${config.url}`);
                    console.log(`token: ${config.headers.Authorization}`);
                    console.log('------------End of request details----------------')
                    reject(error);
                });
        }catch (e)
        {
            console.log(e.message);
        }

    });
    return prom;

}
export async function getFile(objectId,token)
{
    let prom = new Promise(async (resolve, reject) => {
        let config = {
            method: 'get',
            maxBodyLength: Infinity,
            url: `${oauth2JWKSURI}/o/c/dataimporters/${objectId}`,
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };
        axios.request(config)
            .then((response) => {
                let file={
                    contentUrl:response.data.dataFile.link.href
                }
                resolve(file);
            })
            .catch((error) => {
                console.log('Error while reading data file!')
                console.log(error.stack);
                reject(error);
            });
    });
    return prom;

}
export async function getFileContent(url,token)
{
    let prom = new Promise(async (resolve, reject) => {
        let config = {
            method: 'get',
            maxBodyLength: Infinity,
            url: `${oauth2JWKSURI}${url}`,
            headers: {
                //'Authorization': `Bearer ${token}`
            }
        };
        axios.request(config)
            .then((response) => {
                resolve(response.data);
            })
            .catch((error) => {
                console.log(error.message)
                reject(error);
            });
    });
    return prom;

}
