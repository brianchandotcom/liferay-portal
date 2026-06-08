/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {schema} from 'normalizr';

import normalizer from '../normalizer';

describe('Normalizer Middleware', () => {
	it('normalizes a response with a schema', () => {
		const id = '123';
		const name = 'foo';
		const account = {id, name};

		const action = {
			meta: {schema: new schema.Entity('account')},
			payload: account,
			type: 'SUCCESS',
		};

		const normalized = normalizer()((val) => val)(action);

		const {entities, result} = normalized.payload;

		const entity = entities.account[id];

		expect(result).toEqual(id);
		expect(entity.id).toBe(id);
		expect(entity.name).toBe(name);
	});
});
