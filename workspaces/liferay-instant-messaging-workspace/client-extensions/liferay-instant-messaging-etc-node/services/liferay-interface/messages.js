const {getServerToken} = require("../../util/silent-authorization");
const {lrRequest} = require("../../util/request");


const OBJECT_ENDPOINT = '/o/c/messages/';

const post = async (message,token=null) => {

    try {
        token = token || await getServerToken();

        let result = await lrRequest({
            method: 'POST',
            url:`${OBJECT_ENDPOINT}`,
            data:JSON.stringify(message),
        },token);

        return result;

    }catch (error){
        throw new Error(`postIMMessage: ${error.message}`)
    }
}

const get = async (from,to,page=0,pageSize=0,token=null) => {

    try {
        token = token || await getServerToken();

        let result = await lrRequest({
            method: 'GET',
            url:`${OBJECT_ENDPOINT}?page=${page}&pageSize=${pageSize}&filter=((from eq '${from}' and to eq '${to}') or (to eq '${from}' and from eq '${to}'))&sort=date:desc`,
        },token);

        return result;

    }catch (error){
        throw new Error(`postIMMessage: ${error.message}`)
    }

}

module.exports = {
    post,
    get
}
