import {headless_deliveryService} from "../headless-wrapper/headless-delivery.js";
import {getServerToken} from "./silent-authorization.js";
import config from '../util/configTreePath.js';

const viewByAnyone = "Anyone";
const viewOption = viewByAnyone;

const chat_attachments_folder_external_reference_code =
    config['jobs.attachments.folder.external.reference.code'];

export async function getOrCreateMainFolder(globalSiteId, userId) {
    let token = await getServerToken(userId);
    let delivery_srv = new headless_deliveryService(token);
    let folder = await delivery_srv
        .getSiteDocumentsFolderByExternalReferenceCode
        (globalSiteId, chat_attachments_folder_external_reference_code);
    if (folder && folder.id) {
        return folder.id;
    } else {
        let folderObject = {
            "description": "Liferay Chat Attachments Folder",
            "externalReferenceCode": chat_attachments_folder_external_reference_code,
            "name": chat_attachments_folder_external_reference_code,
            "parentDocumentFolderId": 0,
            "viewableBy": viewOption
        }
        let folder = await delivery_srv.postSiteDocumentFolder(globalSiteId, folderObject);
        return folder.id;
    }
}
export async function getOrCreateUserFolder(globalSiteId, uploaderUserId) {
    let folderKey = `user_id_${uploaderUserId}`;
    let token = await getServerToken(uploaderUserId);
    let delivery_srv = new headless_deliveryService(token);
    let folder = await delivery_srv
        .getSiteDocumentsFolderByExternalReferenceCode
        (globalSiteId, folderKey);
    if (folder && folder.id) {
        return folder.id;
    } else {
        let mainFolderId = await getOrCreateMainFolder(globalSiteId, uploaderUserId);
        let folderObject = {
            "description": "Liferay Chat Attachments Folder Uploaded by User " + uploaderUserId,
            "externalReferenceCode": folderKey,
            "name": `User_${uploaderUserId}_Attachments`,
            "viewableBy": viewOption
        }
        let folder = await delivery_srv.postDocumentFolderDocumentFolder(mainFolderId, folderObject);
        return folder.id;
    }
}
