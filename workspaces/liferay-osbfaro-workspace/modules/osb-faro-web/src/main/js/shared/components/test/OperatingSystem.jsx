/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import OperatingSystem from '../OperatingSystem';

jest.unmock('react-dom');

const devices = [
	{
		data: [
			{percentage: 89.80645161290323, type: 'Windows', views: 696},
			{percentage: 0.12903225806451613, type: 'Linux', views: 1},
		],
		percentageOfTotal: 89.93548387096774,
		totalViews: 697,
		type: 'Desktop',
	},
	{
		data: [{percentage: 10.967741935483872, type: 'iOS', views: 85}],
		percentageOfTotal: 10.967741935483872,
		totalViews: 85,
		type: 'Tablet',
	},
];

describe('OperatingSystem', () => {
	it('renders', () => {
		const {container} = render(<OperatingSystem devices={devices} />);

		expect(container).toMatchSnapshot();
	});
});
