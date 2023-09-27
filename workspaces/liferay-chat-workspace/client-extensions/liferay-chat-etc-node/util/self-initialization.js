import config from "../util/configTreePath.js";
import {getServerToken} from "./silent-authorization.js";
import {
    getObjectDefinition,
    postDefinition,
    publishObjectDefinition
} from "../headless-wrapper/headless_object_admin.js";
import ChatObjectDefinition from '../assets/Object_ChatMessage_3478843_20230926190341345.js';

export async function selfInitialization()
{
    if (checkIfEnabled())
    {
        console.log(`Self initialization started!`)
        let token = await getServerToken('System');
        let objectDefInstance = await getObjectDefinition(ChatObjectDefinition.externalReferenceCode,`Bearer ${token}`);
        if (objectDefInstance)
        {
            console.log(`Object is already created!`);
        }else
        {
            console.log(`Creating Object...`);
            let objectInstance = await postDefinition(ChatObjectDefinition,`Bearer ${token}`);
            console.log(`Chat object definition has been created!`);
            console.log(`Publishing chat object definition....`);
            await publishObjectDefinition(objectInstance.id,`Bearer ${token}`);
            console.log(`Chat object definition has been published!`);
        }
    }else
    {
        console.log(`Self initialization is off!`)
    }

}
function checkIfEnabled()
{
    return config['self.initialization'].toLowerCase() === 'yes';
}

