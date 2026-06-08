/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router-dom';
import CommerceMetricCard from '~/commerce/components/CommerceMetricCard';
import CommerceTotalOrderValueQuery, {
	CommerceTotalOrderValueData,
} from '~/commerce/queries/TotalOrderValueQuery';
import client from '~/shared/apollo/client';
import {RangeKeyTimeRanges} from '~/shared/util/constants';
import {
	mockCommerceTotalOrderValueReq,
	mockPreferenceReq,
	mockTimeRangeReq,
} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '123',
		query: {
			rangeKey: RangeKeyTimeRanges.Last30Days,
		},
	}),
}));

const COMMERCE_TOTAL_ORDER_VALUE = '10000000';
const COMMERCE_TREND_PERCENTAGE = 50;

const getData = ({
	classification = 'POSITIVE',
	currencyCode = 'USD',
	percentage = COMMERCE_TREND_PERCENTAGE,
}) => ({
	orderTotalCurrencyValues: [
		{
			__typename: 'orderTotalCurrencyValues',
			currencyCode,
			trend: {
				__typename: 'orderTotalCurrencyValuesTrend',
				percentage,
				trendClassification: classification,
			},
			value: COMMERCE_TOTAL_ORDER_VALUE,
		},
	],
});

const variables = {
	channelId: '123',
	rangeEnd: null,
	rangeKey: 30,
	rangeStart: null,
};

const WrappedComponent = ({data}: {data?: any; defaultLanguageId?: string}) => (
	<Provider store={mockStore()}>
		<ApolloProvider client={client}>
			<StaticRouter>
				<MockedProvider
					mocks={[
						mockTimeRangeReq(),
						mockPreferenceReq(),
						mockCommerceTotalOrderValueReq({
							Query: CommerceTotalOrderValueQuery,
							data,
							variables,
						}),
					]}
				>
					<CommerceMetricCard<CommerceTotalOrderValueData>
						Query={CommerceTotalOrderValueQuery}
						description="this is the description"
						emptyTitle="There are no orders on the selected period."
						label="this is the label"
						mapper={(result) => result?.orderTotalCurrencyValues}
					/>
				</MockedProvider>
			</StaticRouter>
		</ApolloProvider>
	</Provider>
);

describe('CommerceMetricCard', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const {getByText} = render(<WrappedComponent data={getData({})} />);

		await waitForLoadingToBeRemoved(document.body);

		expect(getByText('this is the description')).toBeInTheDocument();
		expect(getByText('this is the label')).toBeInTheDocument();
		expect(
			document.querySelector('.dropdown-range-key-root')
		).toBeInTheDocument();
		expect(getByText('$10,000,000.00')).toBeInTheDocument();
		expect(getByText(`${COMMERCE_TREND_PERCENTAGE}%`)).toBeInTheDocument();
	});

	it('renders with empty state message', async () => {
		const {container, getByText} = render(<WrappedComponent data={[]} />);

		await waitForLoadingToBeRemoved(container);

		expect(
			getByText('There are no orders on the selected period.')
		).toBeInTheDocument();
		expect(
			getByText(
				'Check back later to verify if data has been received from your data sources.'
			)
		).toBeInTheDocument();
	});
});

describe('CommerceMetricCard Classifications', () => {
	afterEach(cleanup);

	it('renders with POSITIVE classification', async () => {
		const {container} = render(
			<WrappedComponent data={getData({classification: 'POSITIVE'})} />
		);

		await waitForLoadingToBeRemoved(container);

		const trendElement = container.querySelector('.analytics-trend')!;
		expect(window.getComputedStyle(trendElement).color).toEqual(
			'rgb(40, 125, 60)'
		);
		expect(
			trendElement.querySelector('.lexicon-icon-caret-top-l')
		).toBeInTheDocument();
	});

	it('renders with NEGATIVE classification', async () => {
		const {container} = render(
			<WrappedComponent data={getData({classification: 'NEGATIVE'})} />
		);

		await waitForLoadingToBeRemoved(container);

		const trendElement = container.querySelector('.analytics-trend')!;
		expect(window.getComputedStyle(trendElement).color).toEqual(
			'rgb(218, 20, 20)'
		);
		expect(
			trendElement.querySelector('.lexicon-icon-caret-top-l')
		).toBeInTheDocument();
	});

	it('renders with NEUTRAL classification', async () => {
		const {container} = render(
			<WrappedComponent data={getData({classification: 'NEUTRAL'})} />
		);

		await waitForLoadingToBeRemoved(container);

		const trendElement = container.querySelector('.analytics-trend')!;
		expect(window.getComputedStyle(trendElement).color).toEqual(
			'rgb(107, 108, 126)'
		);
		expect(
			trendElement.querySelector('.lexicon-icon-caret-top-l')
		).toBeInTheDocument();
	});
});

describe('CommerceMetricCard Trend', () => {
	afterEach(cleanup);

	it('renders with POSITIVE trend', async () => {
		const {container} = render(
			<WrappedComponent
				data={getData({classification: 'POSITIVE', percentage: 50})}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const trendElement = container.querySelector('.analytics-trend')!;
		expect(
			trendElement.querySelector('.lexicon-icon-caret-top-l')
		).toBeInTheDocument();
	});

	it('renders with NEGATIVE trend', async () => {
		const {container} = render(
			<WrappedComponent
				data={getData({classification: 'NEGATIVE', percentage: -50})}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const trendElement = container.querySelector('.analytics-trend')!;
		expect(
			trendElement.querySelector('.lexicon-icon-caret-bottom-l')
		).toBeInTheDocument();
	});

	it('renders with NEUTRAL trend', async () => {
		const {container} = render(
			<WrappedComponent
				data={getData({classification: 'NEUTRAL', percentage: 0})}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const trendElement = container.querySelector('.analytics-trend')!;
		expect(
			trendElement.querySelector('.lexicon-icon-caret-top-l')
		).not.toBeInTheDocument();
		expect(
			trendElement.querySelector('.lexicon-icon-caret-bottom-l')
		).not.toBeInTheDocument();
	});
});

describe('CommerceMetricCard Format Currency', () => {
	afterEach(cleanup);

	it('formats currency and display it in BRL', async () => {
		const {container} = render(
			<WrappedComponent
				data={getData({currencyCode: 'BRL'})}
				defaultLanguageId="pt_BR"
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const currencyValue = document.querySelector(
			'.commerce-card-currency'
		)!;

		expect(currencyValue.textContent!.includes('R$')).toBeTruthy();
	});

	it('formats currency and display it in USD', async () => {
		const {container} = render(
			<WrappedComponent
				data={getData({currencyCode: 'USD'})}
				defaultLanguageId="en_US"
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const currencyValue = document.querySelector(
			'.commerce-card-currency'
		)!;

		expect(currencyValue.textContent!.includes('$')).toBeTruthy();
	});

	it('formats currency and display it in EUR', async () => {
		const {container} = render(
			<WrappedComponent
				data={getData({currencyCode: 'EUR'})}
				defaultLanguageId="es-ES"
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const currencyValue = document.querySelector(
			'.commerce-card-currency'
		)!;

		expect(currencyValue.textContent!.includes('€')).toBeTruthy();
	});
});
