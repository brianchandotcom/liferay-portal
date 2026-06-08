/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {DataSource} from '~/shared/util/records';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';

import {Edit} from '../Edit';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		groupId: '23',
	}),
}));

jest.mock('~/shared/hooks/useRequest', () => ({
	useRequest: jest.fn,
}));

const csvProps = {
	groupId: '23',
	id: '23',
};

describe('Edit', () => {
	it('renders a CSV data-source page', () => {
		const {getByText} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<Edit
						{...csvProps}
						dataSource={new DataSource(data.mockCSVDataSource())}
					/>
				</StaticRouter>
			</Provider>
		);

		expect(getByText('Configure CSV')).toBeInTheDocument();
	});
});
