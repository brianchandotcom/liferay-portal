/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import client from '~/shared/apollo/client';
import {Property} from '~/shared/util/records';
import {mockPreferenceReq} from '~/test/graphql-data';

import {PropertyTypes, RelationalOperators} from '../../utils/constants';
import SessionInput from '../SessionInput';

jest.unmock('react-dom');

const {EQ} = RelationalOperators;

const WrapperComponent = ({children}) => (
	<ApolloProvider client={client}>
		<MockedProvider mocks={[mockPreferenceReq()]}>
			{children}
		</MockedProvider>
	</ApolloProvider>
);

describe('SessionInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {getAllByText, getByText} = render(
			<WrapperComponent>
				<SessionInput
					operatorRenderer={() => <div>operator</div>}
					property={new Property()}
					touched={{customInput: true, dateFilter: true}}
					valid={{customInput: true, dateFilter: true}}
					value={fromJS({
						criterionGroup: {
							items: [
								{operatorName: EQ},
								{
									operatorName: EQ,
									propertyName: 'completeDate',
									value: '2021-01-01',
								},
							],
						},
					})}
				/>
			</WrapperComponent>
		);
		fireEvent.click(getByText('is'));
		fireEvent.click(getByText('on'));

		expect(getAllByText('is')[1]).toBeInTheDocument();
		expect(getByText('is not')).toBeInTheDocument();
		expect(getByText('contains')).toBeInTheDocument();
		expect(getByText('does not contain')).toBeInTheDocument();
		expect(getByText('is known')).toBeInTheDocument();
		expect(getByText('is unknown')).toBeInTheDocument();

		expect(getByText('since')).toBeInTheDocument();
		expect(getByText('after')).toBeInTheDocument();
		expect(getByText('before')).toBeInTheDocument();
		expect(getByText('between')).toBeInTheDocument();
		expect(getByText('ever')).toBeInTheDocument();
		expect(getAllByText('on')[1]).toBeInTheDocument();
	});

	it('renders with "ever"', () => {
		const {getAllByText, getByText} = render(
			<WrapperComponent>
				<SessionInput
					operatorRenderer={() => <div>operator</div>}
					property={new Property()}
					touched={{customInput: true, dateFilter: true}}
					valid={{customInput: true, dateFilter: true}}
					value={fromJS({
						criterionGroup: {
							items: [{operatorName: EQ}],
						},
					})}
				/>
			</WrapperComponent>
		);
		fireEvent.click(getByText('is'));
		fireEvent.click(getByText('ever'));

		expect(getAllByText('is')[1]).toBeInTheDocument();
		expect(getByText('is not')).toBeInTheDocument();
		expect(getByText('contains')).toBeInTheDocument();
		expect(getByText('does not contain')).toBeInTheDocument();
		expect(getByText('is known')).toBeInTheDocument();
		expect(getByText('is unknown')).toBeInTheDocument();

		expect(getByText('since')).toBeInTheDocument();
		expect(getByText('after')).toBeInTheDocument();
		expect(getByText('before')).toBeInTheDocument();
		expect(getByText('between')).toBeInTheDocument();
		expect(getAllByText('ever')[1]).toBeInTheDocument();
		expect(getByText('on')).toBeInTheDocument();
	});

	it('renders a CustomNumberInput', () => {
		const {getByTestId} = render(
			<WrapperComponent>
				<SessionInput
					operatorRenderer={() => <div>operator</div>}
					property={new Property({type: PropertyTypes.SessionNumber})}
					touched={{customInput: true, dateFilter: true}}
					valid={{customInput: true, dateFilter: true}}
					value={fromJS({
						criterionGroup: {items: [{operatorName: EQ}]},
					})}
				/>
			</WrapperComponent>
		);

		expect(getByTestId('number-input')).toBeTruthy();
	});
});
