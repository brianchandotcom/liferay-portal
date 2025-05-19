const Datastore = require("@seald-io/nedb");

const db = new Datastore({filename: './db/data.db', autoload: true});

db.compactDatafile();


const clear = async () => {

    await db.remove({}, { multi: true }, function (err, numRemoved) {
        if (err) {
            console.error('Error clearing the database:', err);
        } else {
            console.log(`Database cleared. ${numRemoved} records removed.`);
        }
    });
    await db.compactDatafile();
}

const clearByQuery = async (query) => {

    await db.remove(query, { multi: true }, function (err, numRemoved) {
        if (err) {
            console.error('Error clearing the database:', err);
        } else {
            console.log(`Database cleared. ${numRemoved} records removed.`);
        }
    });
    await db.compactDatafile();
}

const insert = async (data) => {

    let prom = new Promise((resolve, reject) => {

        db.insert(data, (error, docs) => {
            if (error)
                reject(error);
            else
                resolve(docs);
        });

    });

    return prom;

}

const find = (query) => {

    let prom = new Promise((resolve, reject) => {

        db.find(query, (error, docs) => {
            if (error)
                reject(error);
            else
                resolve(docs);
        });

    });

    return prom;
}

const update = (query, update, options = {}) => {

    let prom = new Promise((resolve, reject) => {

        db.update(query, {$set: {...update}}, options, (error, docs) => {
            if (error)
                reject(error);
            else
                resolve(docs);
        });

    });


    return prom;
}

const remove = (query, options = {multi: true}, compactDatafile = false) => {
    let prom = new Promise((resolve, reject) => {

        db.remove(query, options, (error, docs) => {
            if (error)
                reject(error);
            else {
                resolve(docs);

                if (compactDatafile) {
                    db.compactDatafile();
                }
            }

        });

    });

    return prom;

}

module.exports = {
    insert,
    find,
    update,
    remove,
    clear,
    clearByQuery
}
