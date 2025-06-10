import {request} from "../../utils/request";

export const getDefinitions = async (page,pageSize) => {

    const endPoint = `/o/object-admin/v1.0/object-definitions?page=${page}&pageSize=${pageSize}`;

    return request({
        method: 'GET',
        url: `${endPoint}`,
    });

}
