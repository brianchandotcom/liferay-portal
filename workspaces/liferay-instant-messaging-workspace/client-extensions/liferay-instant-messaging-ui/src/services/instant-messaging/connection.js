/*global Liferay*/
import {config} from "../../utils/constants";

export const  liferayInstantMessagingConnect = async () =>{

    try{
        const oAuthApplication = Liferay.OAuth2Client
            .FromUserAgentApplication(config.agentOauthAppId);

        const [protocol,hostname] = oAuthApplication.homePageURL.split("//")

        const token = await oAuthApplication._getOrRequestToken();

        const socket = new WebSocket(`ws://${hostname}/server?type=im`,token.access_token);

        return socket;

    }catch (error){

        console.error("Unable to connect to Liferay Stream Hub Server",error);
        return null;

    }
}





