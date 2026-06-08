/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';
import {StaticRouter} from 'react-router-dom';

import SelectDataSource from '../SelectDataSource';

jest.unmock('react-dom');

const DATA_SOURCE_ARRAY = [
	{
		iconName: 'csv_logo',
		iconSize: 'xl',
		name: 'test',
		url: '#',
	},
	{
		iconName: 'liferay_logo',
		iconSize: 'xxl',
		name: 'test1',
		url: '#',
	},
];

const mockSections = [
	{
		dataSources: DATA_SOURCE_ARRAY,
		title: 'section 1 title',
	},
	{
		dataSources: [
			{
				iconName: 'salesforce_logo',
				iconSize: 'xxl',
				name: 'test1',
				onClick: noop,
			},
		],
		title: 'section two title',
	},
];

describe('SelectDataSource', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<StaticRouter>
				<SelectDataSource sections={mockSections} />
			</StaticRouter>
		);
		expect(container).toMatchSnapshot();
	});
});
