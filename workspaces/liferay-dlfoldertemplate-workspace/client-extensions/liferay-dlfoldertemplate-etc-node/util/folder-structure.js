import {headless_deliveryService} from "../headless-wrapper/headless-delivery.js";
import {getServerToken} from "./silent-authorization.js";
import config from '../util/configTreePath.js';
import {foldertemplatesService} from '../headless-wrapper/foldertemplates.js';
const viewByAnyone = "Anyone";
const viewOption = viewByAnyone;
const employee_folder_external_reference_code =
    config['employee.folder.external.reference.code'];
const employee_folder_site_id =
    config['employee.folder.site.id'];

async function getOrCreateMainFolder() {

    let token = await getServerToken(0);
    let delivery_srv = new headless_deliveryService(token);
    let folder = await delivery_srv
        .getSiteDocumentsFolderByExternalReferenceCode
        (employee_folder_site_id, employee_folder_external_reference_code);
    if (folder && folder.id) {
        return  folder.id;
    } else {
        let folderObject = {
            "description": "Liferay Employees Folder",
            "externalReferenceCode": employee_folder_external_reference_code,
            "name": employee_folder_external_reference_code,
            "parentDocumentFolderId": 0,
            "viewableBy": viewOption
        }
        let folder = await delivery_srv.postSiteDocumentFolder(employee_folder_site_id, folderObject);
        return folder.id;
    }

}
async function getOrCreateMainFolderById(folderId) {

    let token = await getServerToken(0);
    let delivery_srv = new headless_deliveryService(token);
    let folder = await delivery_srv
        .getDocumentFolder(folderId);
    if (folder && folder.id) {
        return folder.id;
    } else {
        let folderObject = {
            "description": "Liferay Employees Folder",
            "externalReferenceCode": employee_folder_external_reference_code,
            "name": employee_folder_external_reference_code,
            "parentDocumentFolderId": 0,
            "viewableBy": viewOption
        }
        let folder = await delivery_srv.postSiteDocumentFolder(employee_folder_site_id, folderObject);
        return folder.id;
    }
}
async function getOrCreateUserFolder( parentFolderId,folderName,folderDescription) {

    let token = await getServerToken(0);
    let delivery_srv = new headless_deliveryService(token);
    let folderObject = {
        "description":folderDescription,
        "name": `${folderName.trim()}`,
        "viewableBy": viewOption
    }
    let folder = await delivery_srv.postDocumentFolderDocumentFolder(parentFolderId ,folderObject);
    return folder.id;
}
async function getTemplate(templateId)
{
    let token = await getServerToken(0);
    let foldertemplateSrv = new foldertemplatesService(token);
    let dynamicTemplateData = await foldertemplateSrv.getFolderTemplatesPage(templateId);
    let template = dynamicTemplateData.items;
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
    let rootFolder = folders.filter(folder=> folder.id.toString() === rootFolderId.toString())[0];
    let actualFolderId = await getOrCreateUserFolder(actualParentFolderId,rootFolder.name,rootFolder.description);
    let subFolders = folders.filter(folder=> folder.parentID.toString() === rootFolderId.toString());
    if (subFolders.length > 0)
    {
        await subFolders.forEach(async folder=>{
            await traverseFolders(folders,folder.id,actualFolderId);
        });
    }
}
