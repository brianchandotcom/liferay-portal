import {oAuthRequest, request} from "../../utils/request";
import {config} from "../../utils/constants";

export const getDefinitions = () => {

    return oAuthRequest({
        url:`${config.objectDefinitionEndPoint}?page=0`,
        method: 'GET',
    });


}
