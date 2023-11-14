import {headless_deliveryService} from "../headless-wrapper/headless-delivery.js";
import {getServerToken} from "./silent-authorization.js";
import config from '../util/configTreePath.js';
import axios from 'axios';
import {foldertemplatesService} from '../headless-wrapper/foldertemplates.js';
const lxcDXPMainDomain = config['com.liferay.lxc.dxp.mainDomain'] ||
    config['com.liferay.sh.dxp.mainDomain'];
const lxcDXPServerProtocol = config['com.liferay.lxc.dxp.server.protocol'] ||
    config['com.liferay.sh.dxp.server.protocol'];

const templateId = config['templateId']
const oauth2JWKSURI = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}`;

const viewByOwner = "Owner";
const viewByAnyone = "Anyone";
const viewOption = viewByAnyone;

const employee_folder_external_reference_code =
    config['employee.folder.external.reference.code'];
const employee_folder_site_id =
    config['employee.folder.site.id'];

async function getOrCreateMainFolder() {

    let prom = new Promise(async (resolve, reject) => {
        let token = await getServerToken(0);
        let delivery_srv = new headless_deliveryService(token);
        let folder = await delivery_srv
            .getSiteDocumentsFolderByExternalReferenceCode
            (employee_folder_site_id, employee_folder_external_reference_code);
        if (folder && folder.id) {
            console.log(`Main folder is available!, Folder ID: ${folder.id}`);
            resolve(folder.id);
        } else {
            console.log(`main folder is not available!`);
            let folderObject = {
                "description": "Liferay Employees Folder",
                "externalReferenceCode": employee_folder_external_reference_code,
                "name": employee_folder_external_reference_code,
                "parentDocumentFolderId": 0,
                "viewableBy": viewOption
            }
            let folder = await delivery_srv.postSiteDocumentFolder(employee_folder_site_id, folderObject);
            console.log(`Main folder has been created!, Folder ID: ${folder.id}`);
            resolve(folder.id);
        }
    });
    return prom;

}
async function getOrCreateMainFolderById(folderId) {

    let prom = new Promise(async (resolve, reject) => {
        let token = await getServerToken(0);
        let delivery_srv = new headless_deliveryService(token);
        let folder = await delivery_srv
            .getDocumentFolder(folderId);
        if (folder && folder.id) {
            console.log(`Main folder is available!, Folder ID: ${folder.id}`);
            resolve(folder.id);
        } else {
            console.log(`main folder is not available!`);
            let folderObject = {
                "description": "Liferay Employees Folder",
                "externalReferenceCode": employee_folder_external_reference_code,
                "name": employee_folder_external_reference_code,
                "parentDocumentFolderId": 0,
                "viewableBy": viewOption
            }
            let folder = await delivery_srv.postSiteDocumentFolder(employee_folder_site_id, folderObject);
            console.log(`Main folder has been created!, Folder ID: ${folder.id}`);
            resolve(folder.id);
        }
    });
    return prom;

}
async function getOrCreateUserFolder( parentFolderId,folderName,folderDescription) {

    let prom = new Promise(async (resolve, reject) => {
        let token = await getServerToken(0);
        let delivery_srv = new headless_deliveryService(token);
        let folderObject = {
            "description":folderDescription,
            "name": `${folderName.trim()}`,
            "viewableBy": viewOption
        }
        let folder = await delivery_srv.postDocumentFolderDocumentFolder(parentFolderId ,folderObject);
        if (!folder.id)
        {
            console.log(folder)
        }
        console.log(`User folder has been created!, Folder ID: ${folder.id}`);
        resolve(folder.id);
    });
    return prom;
}
async function getTemplate(templateId)
{
    console.log(`template id ${templateId}`);
    let token = await getServerToken(0);
    let foldertemplateSrv = new foldertemplatesService(token);
    let dynamicTemplateData = await foldertemplateSrv.getFolderTemplatesPage(templateId);
    let template = dynamicTemplateData.items;
    console.log(`template id ${templateId} has ${template.length} items`);
    return template;
}

export async function start(userId, templateId) {
    let template = await getTemplate(templateId);
    let root = template.filter(folder => folder.root)[0];
    root.name=userId;
    let mainFolderId = await getOrCreateMainFolder();
    await traverseFolders(template, root.id, mainFolderId);
}

export async function startCustom(rootFolderName, templateId,containerFolderId) {
    let template = await getTemplate(templateId);
    let root = template.filter(folder => folder.root)[0];
    root.name=rootFolderName;
    let mainFolderId = await getOrCreateMainFolderById(containerFolderId);
    await traverseFolders(template, root.id, mainFolderId);
}

async function traverseFolders(folders,rootFolderId,actualParentFolderId)
{
    let rootFolder = folders.filter(folder=> folder.id == rootFolderId)[0];
    let actualFolderId = await getOrCreateUserFolder(actualParentFolderId,rootFolder.name,rootFolder.description);
    console.log(`folder id ${rootFolderId} created!`);
    let subFolders = folders.filter(folder=> folder.parentID == rootFolderId);
    console.log(`sub folders size ${subFolders.length}`)
    if (subFolders.length > 0)
    {
        await subFolders.forEach(async folder=>{
            await traverseFolders(folders,folder.id,actualFolderId);
        });
    }
    else
        console.log('no leafs');

}
