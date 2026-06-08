/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {fireEvent, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {DataTypes} from '~/event-analysis/utils/types';
import client from '~/shared/apollo/client';
import {mockPreferenceReq} from '~/test/graphql-data';
import mockStore from '~/test/mock-store';

import {
	FunctionalOperators,
	RelationalOperators,
} from '../../../../utils/constants';
import ValueInput from '../ValueInput';

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

describe('ValueInput', () => {
	it('renders', () => {
		const {container, getAllByText, getByText} = render(
			<WrapperComponent>
				<ValueInput
					dataType={DataTypes.Boolean}
					onChange={jest.fn()}
					operatorName={RelationalOperators.EQ}
					touched={false}
					valid
					value="true"
				/>
			</WrapperComponent>
		);
		fireEvent.click(getByText('True'));

		expect(getAllByText('True')[1]).toBeTruthy();
		expect(getByText('False')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});

	it.each`
		dataType             | operatorName                    | value                                       | testId
		${DataTypes.Boolean} | ${RelationalOperators.EQ}       | ${'true'}                                   | ${'attribute-value-boolean-input'}
		${DataTypes.Date}    | ${RelationalOperators.EQ}       | ${'2020-12-12'}                             | ${'date-input'}
		${DataTypes.Date}    | ${FunctionalOperators.Between}  | ${{end: '2020-12-12', start: '2020-12-01'}} | ${'date-range-input'}
		${DataTypes.Number}  | ${RelationalOperators.GT}       | ${1000}                                     | ${'number-input'}
		${DataTypes.Number}  | ${FunctionalOperators.Between}  | ${{end: 1000, start: 1}}                    | ${'between-number-end-input'}
		${DataTypes.String}  | ${FunctionalOperators.Contains} | ${'Stuff'}                                  | ${'attribute-value-string-input'}
	`(
		'should find $testId if dataType is $dataType and operatorName is $operatorName',
		({dataType, operatorName, testId, value}) => {
			const {queryByTestId} = render(
				<WrapperComponent>
					<ValueInput
						dataType={dataType}
						onChange={jest.fn()}
						operatorName={operatorName}
						touched={false}
						valid
						value={value}
					/>
				</WrapperComponent>
			);

			expect(queryByTestId(testId)).toBeTruthy();
		}
	);
});
