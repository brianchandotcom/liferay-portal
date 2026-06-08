/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {buildLanguages} from '../buildLanguages';

describe('buildLanguages', () => {
	it('returns every key declared in the Languages contract', () => {
		const languages = buildLanguages('Acme');

		expect(Object.keys(languages).sort()).toEqual(
			[
				'connectDescription',
				'connectTitle',
				'endpointHelper',
				'endpointLabel',
				'tokenLabel',
			].sort()
		);
	});

	it('substitutes the display name in connect-related strings', () => {
		const languages = buildLanguages('Acme');

		expect(languages.connectTitle).toContain('Acme');
		expect(languages.connectDescription).toContain('Acme');
	});

	it('substitutes the display name in endpoint and token labels', () => {
		const languages = buildLanguages('Acme');

		expect(languages.endpointHelper).toContain('Acme');
		expect(languages.endpointLabel).toContain('Acme');
		expect(languages.tokenLabel).toContain('Acme');
	});

	it('returns string values for every key', () => {
		const languages = buildLanguages('Acme');

		Object.values(languages).forEach((value) => {
			expect(typeof value).toBe('string');
		});
	});
});
