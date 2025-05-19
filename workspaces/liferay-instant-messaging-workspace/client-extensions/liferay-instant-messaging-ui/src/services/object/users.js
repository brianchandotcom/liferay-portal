import {request} from "../../utils/request";

export const getUserRoles = async (page=0,pageSize=0) => {

    const endPoint = `/o/headless-admin-user/v1.0/roles?page=${page}&pageSize=${pageSize}`;

    return request({
        method: 'GET',
        url: `${endPoint}`,
    });

}
