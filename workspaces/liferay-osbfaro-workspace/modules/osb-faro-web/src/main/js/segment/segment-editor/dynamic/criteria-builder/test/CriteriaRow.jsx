/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {Map, fromJS} from 'immutable';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {Provider} from 'react-redux';
import client from '~/shared/apollo/client';
import {mockPreferenceReq} from '~/test/graphql-data';
import mockStore from '~/test/mock-store';

import CriteriaRow from '../CriteriaRow';

jest.unmock('react-dom');

const WrapperComponent = ({children}) => (
	<ApolloProvider client={client}>
		<Provider store={mockStore()}>
			<MockedProvider mocks={[mockPreferenceReq()]}>
				{children}
			</MockedProvider>
		</Provider>
	</ApolloProvider>
);

describe('CriteriaRow', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {getAllByText, getByText} = render(
			<WrapperComponent>
				<DndProvider backend={HTML5Backend}>
					<CriteriaRow
						criterion={{
							operatorName: 'sessions-filter',
							propertyName: 'context/referrer',
							rowId: 'row_0',
							touched: false,
							valid: true,
							value: fromJS({
								criterionGroup: {
									conjunctionName: 'and',
									criteriaGroupId: 'group_0',
									items: [
										{
											operatorName: 'ne',
											propertyName: 'context/referrer',
											rowId: 'row_1',
											touched: false,
											valid: true,
										},
										{
											operatorName: 'gt',
											propertyName: 'completeDate',
											rowId: 'row_2',
											touched: false,
											valid: true,
										},
									],
								},
							}),
						}}
						referencedAssetsIMap={new Map()}
						referencedPropertiesIMap={new Map()}
					/>
				</DndProvider>
			</WrapperComponent>
		);

		fireEvent.click(getByText('is not'));

		expect(getByText('is')).toBeInTheDocument();
		expect(getAllByText('is not')[1]).toBeInTheDocument();
		expect(getByText('contains')).toBeInTheDocument();
		expect(getByText('does not contain')).toBeInTheDocument();
		expect(getByText('is known')).toBeInTheDocument();
		expect(getByText('is unknown')).toBeInTheDocument();
	});

	it('renders w/ Non-Existent Property message', () => {
		const {queryByText} = render(
			<WrapperComponent>
				<DndProvider backend={HTML5Backend}>
					<CriteriaRow
						referencedAssetsIMap={new Map()}
						referencedPropertiesIMap={new Map()}
					/>
				</DndProvider>
			</WrapperComponent>
		);

		expect(queryByText('Attribute no longer exists.')).toBeTruthy();
	});
});
