/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {BrowserRouter} from 'react-router-dom';
import {fetchDistributionTabs} from '~/shared/actions/preferences';
import mockStore from '~/test/mock-store';

import DistributionCard from '../index';

jest.mock('~/shared/actions/preferences');

jest.unmock('react-dom');

describe('DistributionCard', () => {
	afterEach(() => {
		fetchDistributionTabs.mockClear();

		cleanup();
	});

	it('renders', () => {
		fetchDistributionTabs.mockReturnValue(() => Promise.resolve({}));

		const {getByText} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<DistributionCard
						distributionKey="individualsDashboard"
						fetchDistribution={() => Promise.resolve()}
					/>
				</BrowserRouter>
			</Provider>
		);

		expect(
			getByText(Liferay.Language.get('explore-breakdown'))
		).toBeInTheDocument();
	});

	it('renders empty state via props with noResultsRenderer', () => {
		fetchDistributionTabs.mockReturnValue(() => Promise.resolve({}));

		const {getByText} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<DistributionCard
						distributionKey="individualsDashboard"
						fetchDistribution={() => Promise.resolve()}
						noResultsRenderer={() => <div>empty state</div>}
					/>
				</BrowserRouter>
			</Provider>
		);

		expect(getByText('empty state')).toBeInTheDocument();
	});

	it('renders w/ showAddDataSource', () => {
		fetchDistributionTabs.mockReturnValue(() => Promise.resolve({}));

		const {getByText} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<DistributionCard
						distributionKey="individualsDashboard"
						fetchDistribution={() => Promise.resolve()}
						groupId="123"
						showAddDataSource
					/>
				</BrowserRouter>
			</Provider>
		);

		expect(
			getByText(Liferay.Language.get('add-data-source'))
		).toBeInTheDocument();
		expect(
			getByText(Liferay.Language.get('distribution'))
		).toBeInTheDocument();
	});

	it('renders /w AddPropertyForm', () => {
		fetchDistributionTabs.mockReturnValue(() => Promise.resolve({}));

		const {getByText} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<DistributionCard
						distributionKey="123"
						fetchDistribution={() => Promise.resolve()}
						id="123"
					/>
				</BrowserRouter>
			</Provider>
		);

		expect(
			getByText(
				Liferay.Language.get('add-a-breakdown-by-individual-attribute')
			)
		).toBeInTheDocument();
		expect(getByText(Liferay.Language.get('save'))).toBeInTheDocument();
	});
});
