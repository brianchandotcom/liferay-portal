const fs = require('fs');
const path = require('path');

const rootFolder = path.resolve(__dirname, '../','../');


const get = (contextObjectDefinitionID) => {


    let folderPath = path.join(rootFolder,'contexts', contextObjectDefinitionID);

    try {
        const files = fs.readdirSync(folderPath);
        let contexts = [];

        files.forEach((file) => {
            const filePath = path.join(folderPath, file);
            const stats = fs.statSync(filePath);

            if (stats.isFile() && path.extname(file).toLowerCase() === '.txt') {
                const fileContent = fs.readFileSync(filePath, 'utf8');
                contexts.push(fileContent);

            }
        });

        return contexts;
    } catch (err) {
        console.error('Error reading files:', err);
        return '';
    }


}

module.exports = {
    get
}
