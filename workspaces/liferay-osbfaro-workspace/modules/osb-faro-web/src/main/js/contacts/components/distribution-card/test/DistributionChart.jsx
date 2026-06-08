/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {BrowserRouter} from 'react-router-dom';
import {FieldContexts} from '~/shared/util/constants';
import {DistributionTab} from '~/shared/util/records';
import mockStore from '~/test/mock-store';

import DistributionChart from '../DistributionChart';

jest.unmock('react-dom');

describe('DistributionCard DistributionChart', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<DistributionChart
						distributionKey="individualsDashboard"
						fetchDistribution={() => Promise.reject()}
						selectedTab={
							new DistributionTab({
								context: FieldContexts.Demographics,
								propertyType: 'number',
								title: 'Tab 1',
							})
						}
						viewAllLink="test/:id"
					/>
				</BrowserRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders empty state via props with noResultsRenderer', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<DistributionChart
						distributionKey="individualsDashboard"
						fetchDistribution={() => Promise.reject()}
						noResultsRenderer={() => <div>empty state</div>}
						selectedTab={
							new DistributionTab({
								context: FieldContexts.Demographics,
								propertyType: 'number',
								title: 'Tab 1',
							})
						}
						viewAllLink="test/:id"
					/>
				</BrowserRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
