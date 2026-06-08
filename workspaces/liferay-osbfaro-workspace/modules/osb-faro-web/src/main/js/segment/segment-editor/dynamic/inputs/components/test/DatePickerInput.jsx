/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import client from '~/shared/apollo/client';
import {mockPreferenceReq} from '~/test/graphql-data';
import mockStore from '~/test/mock-store';

import DatePickerInput from '../DatePickerInput';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456',
		groupId: '2000',
		query: {
			rangeKey: '30',
		},
	}),
}));

jest.mock('~/shared/hooks/useTimeZone', () => ({
	useTimeZone: () => ({
		timeZoneId: 'UTC',
	}),
}));

const WrapperComponent = ({children}) => (
	<ApolloProvider client={client}>
		<Provider store={mockStore()}>
			<MockedProvider mocks={[mockPreferenceReq()]}>
				{children}
			</MockedProvider>
		</Provider>
	</ApolloProvider>
);

describe('DatePickerInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		render(
			<WrapperComponent>
				<DatePickerInput onChange={jest.fn()} value="2020-12-12" />
			</WrapperComponent>
		);

		expect(screen.getByTestId('date-input')).toBeInTheDocument();
	});

	it('renders w/ DateRangeInput', () => {
		const {getByTestId} = render(
			<WrapperComponent>
				<DatePickerInput
					isRange
					onChange={jest.fn()}
					value={{end: '2020-12-12', start: '2020-12-20'}}
				/>
			</WrapperComponent>
		);

		expect(getByTestId('date-range-input')).toBeTruthy();
	});
});
