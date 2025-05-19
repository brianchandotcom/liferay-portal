import {oAuthRequest} from "../../utils/request";


export const getContacts = async () => {

    const endPoint = `/im/contacts`;

    return oAuthRequest({
        method: 'GET',
        url: `${endPoint}`,
    });

}
