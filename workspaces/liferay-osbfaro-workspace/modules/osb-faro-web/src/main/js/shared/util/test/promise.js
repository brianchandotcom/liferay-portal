/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sequence} from '../promise';

describe('sequence', () => {
	it('resolves with the first truthy error and skip remaining validators', () => {
		expect.assertions(1);

		const errorVal = 'error';
		const secondValidator = jest.fn(() => Promise.resolve('other'));

		const response = sequence([
			() => Promise.resolve(''),
			() => Promise.resolve(errorVal),
			secondValidator,
		])();

		return response.then((result) => {
			expect(result).toBe(errorVal);
		});
	});

	it('resolves with a falsy value when all validators pass', () => {
		expect.assertions(1);

		const response = sequence([
			() => Promise.resolve(''),
			() => Promise.resolve(''),
		])();

		return expect(response).resolves.toBeFalsy();
	});
});
