/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const Datastore = require('@seald-io/nedb');

const db = new Datastore({autoload: true, filename: './db/data.db'});

db.compactDatafile();

const clear = async () => {
	await db.remove({}, {multi: true}, (error, numRemoved) => {
		if (error) {
			console.error('Error clearing the database:', error); // eslint-disable-line no-console
		}
		else {
			console.log(`Database cleared. ${numRemoved} records removed.`); // eslint-disable-line no-console
		}
	});
	await db.compactDatafile();
};

const clearByQuery = async (query) => {
	await db.remove(query, {multi: true}, (error, numRemoved) => {
		if (error) {
			console.error('Error clearing the database:', error); // eslint-disable-line no-console
		}
		else {
			console.log(`Database cleared. ${numRemoved} records removed.`); // eslint-disable-line no-console
		}
	});
	await db.compactDatafile();
};

const insert = async (data) => {
	const prom = new Promise((resolve, reject) => {
		db.insert(data, (error, docs) => {
			if (error) {
				reject(error);
			}
			else {
				resolve(docs);
			}
		});
	});

	return prom;
};

const find = (query) => {
	const prom = new Promise((resolve, reject) => {
		db.find(query, (error, docs) => {
			if (error) {
				reject(error);
			}
			else {
				resolve(docs);
			}
		});
	});

	return prom;
};

const update = (query, update, options = {}) => {
	const prom = new Promise((resolve, reject) => {
		db.update(query, {$set: {...update}}, options, (error, docs) => {
			if (error) {
				reject(error);
			}
			else {
				resolve(docs);
			}
		});
	});

	return prom;
};

const remove = (query, options = {multi: true}, compactDatafile = false) => {
	const prom = new Promise((resolve, reject) => {
		db.remove(query, options, (error, docs) => {
			if (error) {
				reject(error);
			}
			else {
				resolve(docs);

				if (compactDatafile) {
					db.compactDatafile();
				}
			}
		});
	});

	return prom;
};

module.exports = {
	clear,
	clearByQuery,
	find,
	insert,
	remove,
	update,
};
