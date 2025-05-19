import {request} from "../../utils/request";
import {getCurrentSite} from "../../utils/util";


export const getRootFolders = async (page=0,pageSize=0) => {

    const endPoint = `/o/headless-delivery/v1.0/sites/${getCurrentSite()}/document-folders?page=${page}&pageSize=${pageSize}`;

    return request({
        method: 'GET',
        url: `${endPoint}`,
    });

}

export const getRootFiles = async (page=0,pageSize=0) => {

    const endPoint = `/o/headless-delivery/v1.0/sites/${getCurrentSite()}/documents?page=${page}&pageSize=${pageSize}`;

    return request({
        method: 'GET',
        url: `${endPoint}`,
    });

}

export const getFolderFiles = async (folderId,page=0,pageSize=0) => {

    const endPoint = `/o/headless-delivery/v1.0/document-folders/${folderId}/documents?page=${page}&pageSize=${pageSize}`;

    return request({
        method: 'GET',
        url: `${endPoint}`,
    });

}

export const getFolderSubfolders = async (folderId,page=0,pageSize=0) => {

    const endPoint = `/o/headless-delivery/v1.0/document-folders/${folderId}/document-folders?page=${page}&pageSize=${pageSize}`;

    return request({
        method: 'GET',
        url: `${endPoint}`,
    });

}
