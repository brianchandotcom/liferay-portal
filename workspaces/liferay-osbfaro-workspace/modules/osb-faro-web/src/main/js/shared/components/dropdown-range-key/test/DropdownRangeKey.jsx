/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {SEVEN_MONTHS} from '~/shared/util/constants';
import {mockPreferenceReq, mockTimeRangeReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import {DropdownRangeKey} from '../DropdownRangeKey';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useTimeZone', () => ({
	useTimeZone: () => ({
		timeZoneId: 'UTC',
	}),
}));

const WrapperComponent = ({children, retentionPeriodTimeRange}) => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/workspace/23']}>
			<Route path="/workspace/:groupId">
				<MockedProvider
					cache={
						new InMemoryCache({
							addTypename: false,
							freezeResults: false,
						})
					}
					mocks={[
						mockTimeRangeReq(),
						mockPreferenceReq(retentionPeriodTimeRange),
					]}
				>
					{children}
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('DropdownRangeKey', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const {container} = render(
			<WrapperComponent>
				<DropdownRangeKey />
			</WrapperComponent>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('displays a message with retention period for 13 months on date picker', async () => {
		const {getByTestId, getByText} = render(
			<WrapperComponent>
				<DropdownRangeKey legacy={false} />
			</WrapperComponent>
		);

		await waitForLoadingToBeRemoved(document.body);

		fireEvent.click(getByText(/custom range/i));

		const previousMonthButton = getByTestId('previous-month');

		// Expect to disable prev button when month is 14

		for (let month = 1; month < 14; month++) {
			expect(previousMonthButton).not.toBeDisabled();

			if (month === 14) {
				expect(previousMonthButton).toBeDisabled();
			}

			fireEvent.click(previousMonthButton);
		}

		expect(
			getByText(
				"Dates prior to 13 months cannot be selected due to your workspace's data retention period."
			)
		).toBeInTheDocument();
	});

	it('displays a message with retention period for 7 months on date picker', async () => {
		const {getByTestId, getByText} = render(
			<WrapperComponent retentionPeriodTimeRange={SEVEN_MONTHS}>
				<DropdownRangeKey legacy={false} />
			</WrapperComponent>
		);

		await waitForLoadingToBeRemoved(document.body);

		fireEvent.click(getByText(/custom range/i));

		const previousMonthButton = getByTestId('previous-month');

		// Expect to disable prev button when month is 8

		for (let month = 1; month < 8; month++) {
			expect(previousMonthButton).not.toBeDisabled();

			if (month === 8) {
				expect(previousMonthButton).toBeDisabled();
			}

			fireEvent.click(previousMonthButton);
		}
		expect(
			getByText(
				"Dates prior to 7 months cannot be selected due to your workspace's data retention period."
			)
		).toBeInTheDocument();
	});
});
