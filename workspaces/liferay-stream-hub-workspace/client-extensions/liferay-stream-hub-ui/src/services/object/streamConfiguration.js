import {request} from "../../utils/request";

export const getStreamConfigurationEntries = async (page, pageSize) => {

    const endPoint = `/o/c/streamhubconfigurations?page=${page}&pageSize=${pageSize}`;

    return request({
        method: 'GET',
        url: `${endPoint}`,
    });

}

export const postStreamConfigurationEntry= async (data) => {

    const endPoint = `/o/c/streamhubconfigurations`;

    return request({
        method: 'POST',
        url: `${endPoint}`,
        data:data
    });

}

export const standaloneStreamConfigurationEntry= async (id) => {

    const endPoint = `/o/c/streamhubconfigurations`;

    return request({
        method: 'PUT',
        url: `${endPoint}/${id}/object-actions/lrSHStandalone`
    });

}

export const putStreamConfigurationEntry= async (id, data) => {

    const endPoint = `/o/c/streamhubconfigurations/${id}`;

    return request({
        method: 'PUT',
        url: `${endPoint}`,
        data:data
    });

}

export const deleteStreamConfigurationEntry= async (entryId) => {

    const endPoint = `/o/c/streamhubconfigurations/${entryId}`;

    return request({
        method: 'Delete',
        url: `${endPoint}`
    });

}
