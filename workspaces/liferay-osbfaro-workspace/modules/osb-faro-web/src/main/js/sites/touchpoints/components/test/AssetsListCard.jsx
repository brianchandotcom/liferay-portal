/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {render} from '@testing-library/react';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import client from '~/shared/apollo/client';
import BasePage from '~/shared/components/base-page';

import AssetsListCard from '../AssetsListCard';

jest.unmock('react-dom');

const items = [
	{
		assetId: '01234',
		assetType: 'journal',
		interactions: 1,
		title: '231494203',
		type: 'Web Content',
	},
	{
		assetId: '01234',
		assetType: 'journal',
		interactions: 1,
		title: '231494203',
		type: 'Web Content',
	},
	{
		assetId: '01234',
		assetType: 'journal',
		interactions: 1,
		title: '231494203',
		type: 'Web Content',
	},
	{
		assetId: '01234',
		assetType: 'journal',
		interactions: 1,
		title: '231494203',
		type: 'Web Content',
	},
	{
		assetId: '01234',
		assetType: 'journal',
		interactions: 1,
		title: '231494203',
		type: 'Web Content',
	},
	{
		assetId: '01234',
		assetType: 'journal',
		interactions: 1,
		title: '231494203',
		type: 'Web Content',
	},
	{
		assetId: '01234',
		assetType: 'journal',
		interactions: 1,
		title: '231494203',
		type: 'Web Content',
	},
	{
		assetId: '01234',
		assetType: 'journal',
		interactions: 1,
		title: '231494203',
		type: 'Web Content',
	},
	{
		assetId: '01234',
		assetType: 'journal',
		interactions: 1,
		title: '231494203',
		type: 'Web Content',
	},
];

describe('AssetsListCard', () => {
	const MOCK_CONTEXT = {
		router: {
			params: {
				channelId: 123,
				groupId: '2000',
				touchpoint: 'www.liferay.com',
			},
			query: {
				rangeKey: '30',
			},
		},
	};

	const WrappedComponent = (props) => (
		<ApolloProvider client={client}>
			<BasePage.Context.Provider value={MOCK_CONTEXT}>
				<BrowserRouter>
					<AssetsListCard
						{...props}
						rangeSelectors={{rangeKey: '30'}}
					/>
				</BrowserRouter>
			</BasePage.Context.Provider>
		</ApolloProvider>
	);

	it('renders component', () => {
		const {container} = render(<WrappedComponent />);

		expect(container).toMatchSnapshot();
	});

	it('renders component with items', () => {
		const {container} = render(<WrappedComponent items={items} />);

		expect(container).toMatchSnapshot();
	});
});
