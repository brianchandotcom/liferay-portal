
const getSystemObjectEntryModelData = (entry) => {

    const matchingProperties = Object.keys(entry)
        .filter(key => key.startsWith("model") && !key.startsWith("modelDTO"))
        .reduce((result, key) => {
            result[key] = entry[key];
            return result;
        }, {});

    if (matchingProperties && Object.keys(matchingProperties).length > 0) {

        return entry[Object.keys(matchingProperties)[0]];
    }

}

const getFilteredObject = (objectEntry, configurationFields)=> {

    const filteredObject = Object.keys(objectEntry)
        .filter(key => configurationFields.includes(key))
        .reduce((acc, key) => {
            acc[key] = objectEntry[key];
            return acc;
        }, {});
    return filteredObject;
}

module.exports = {
    getSystemObjectEntryModelData,
    getFilteredObject
};
