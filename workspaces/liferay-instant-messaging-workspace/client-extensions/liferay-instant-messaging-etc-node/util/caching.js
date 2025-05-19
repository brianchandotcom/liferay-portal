const NodeCache = require( "node-cache" );
const cache = new NodeCache( { stdTTL: 100, checkperiod: 5 } );
const TTL = 120 * 60;
const callbackMap = new Map();

cache.on("expired",(expiredKey,value)=>{

    if(callbackMap.has(expiredKey)){

        callbackMap.get(expiredKey)();

    }

})

const putCacheEntry = (key, data, duration = TTL) => {
    cache.set(key, data, duration);
}

const getCacheEntry = (key) => {
    return cache.get(key);
}

const hasCacheKey = (key) => {
    return cache.has(key);
}
const deleteCacheEntry = (key) => {
    return cache.del(key)
}

const setCacheEntryCallback = (key, callback) => {

    callbackMap.set(key,callback);
}

const clearCacheEntries = () => {
    cache.close();
}

module.exports = {
    putCacheEntry,
    getCacheEntry,
    clearCacheEntries,
    hasCacheKey,
    deleteCacheEntry,
    setCacheEntryCallback
};
