/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import BasePage from '~/shared/components/base-page';
import {mockPreferenceReq, mockTimeRangeReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import VisitorsByTimeCard, {
	formatHour,
	renderTooltip,
} from '../VisitorsByTimeCard';

jest.unmock('react-dom');

const MOCK_CONTEXT = {
	rangeKey: {defaultValue: '30'},
	router: {
		params: {
			channelId: '123',
			groupId: '2000',
		},
		query: {
			rangeKey: '30',
		},
	},
};

const WrappedComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/workspace/2000/123']}>
			<Route path="/workspace/:groupId/:channelId">
				<BasePage.Context.Provider value={MOCK_CONTEXT}>
					<MockedProvider
						cache={
							new InMemoryCache({
								addTypename: false,
								freezeResults: false,
							})
						}
						mocks={[mockTimeRangeReq(), mockPreferenceReq()]}
					>
						<VisitorsByTimeCard {...props} />
					</MockedProvider>
				</BasePage.Context.Provider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('VisitorsByTimeCard', () => {
	it('render', async () => {
		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});

describe('renderTooltip', () => {
	it('render', () => {
		const {container} = render(
			renderTooltip({column: 'Sunday', row: 12, value: 98})
		);

		expect(container).toMatchSnapshot();
	});
});

describe('formatHour', () => {
	it.each`
		hour  | retVal
		${0}  | ${'12 AM'}
		${6}  | ${'6 AM'}
		${11} | ${'11 AM'}
		${12} | ${'12 PM'}
		${18} | ${'6 PM'}
	`('return $retVal when formatting $hour', ({hour, retVal}) => {
		expect(formatHour(hour)).toBe(retVal);
	});
});
