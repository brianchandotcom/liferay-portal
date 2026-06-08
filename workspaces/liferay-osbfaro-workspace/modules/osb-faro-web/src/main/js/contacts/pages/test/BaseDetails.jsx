/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import BaseDetails from '../BaseDetails';

jest.unmock('react-dom');

const DefaultComponent = (props) => (
	<StaticRouter>
		<Provider store={mockStore()}>
			<BaseDetails
				dataSourceFn={() => Promise.resolve(data.mockAccountDetails())}
				groupId="23"
				id="test"
				{...props}
			/>
		</Provider>
	</StaticRouter>
);

describe('BaseDetails', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders w/o loading', () => {
		const {container} = render(<DefaultComponent />);

		expect(container.querySelector('.loading-animation')).toBeTruthy();
	});

	it('renders w/ ErrorDisplay', async () => {
		const {container, queryByText} = render(
			<DefaultComponent dataSourceFn={() => Promise.reject({})} />
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(queryByText('Reload')).toBeTruthy();
	});
});
