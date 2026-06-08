/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Colors} from '~/shared/util/charts';

import {Icons, getIcon, getStatsColor} from '../metrics';

describe('getStatsColor', () => {
	it('is return the neutral color if no color is specificated', () => {
		const color = getStatsColor();

		expect(color).toEqual(Colors.neutral);
	});

	it('is return the positive color', () => {
		const color = getStatsColor('POSITIVE');

		expect(color).toEqual(Colors.positive);
	});

	it('is return the negative color', () => {
		const color = getStatsColor('NEGATIVE');

		expect(color).toEqual(Colors.negative);
	});

	it('is return the neutral color', () => {
		const color = getStatsColor('NEUTRAL');

		expect(color).toEqual(Colors.neutral);
	});
});

describe('getIcon', () => {
	it('is return the current icon based in a positive number', () => {
		const icon = getIcon(10);

		expect(icon).toEqual(Icons.positive);
	});

	it('is return the current icon based in a negative number', () => {
		const icon = getIcon(-10);

		expect(icon).toEqual(Icons.negative);
	});

	it('is return the current icon based in zero number', () => {
		const icon = getIcon(0);

		expect(icon).toEqual(Icons.neutral);
	});
});
