import {oAuthRequest} from "../../utils/request";


export const getChatLog = async (from,to) => {

    const endPoint = `/im/chatlog/${from}/${to}`;

    return oAuthRequest({
        method: 'GET',
        url: `${endPoint}`,
    });

}
