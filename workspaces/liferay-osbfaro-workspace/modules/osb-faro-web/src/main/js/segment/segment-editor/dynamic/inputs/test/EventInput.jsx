/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {render, screen, waitFor} from '@testing-library/react';
import {fromJS} from 'immutable';
import {range} from 'lodash';
import React from 'react';
import {MemoryRouter, Route} from 'react-router-dom';
import {Property} from '~/shared/util/records';
import {Routes} from '~/shared/util/router';
import * as data from '~/test/data';
import {mockEventPropertiesReq} from '~/test/graphql-data';
import * as pedantic from '~/test/pedantic';

import {createCustomValueMap} from '../../utils/custom-inputs';
import EventInput from '../EventInput';

jest.unmock('react-dom');

jest.mock('../components/attribute-conjunction-input', () => () => (
	<div>AttributeConjunctionInput</div>
));

const mockValue = createCustomValueMap([
	{
		key: 'criterionGroup',
		value: [
			{
				operatorName: 'eq',
				propertyName: 'eventDefinitionId',
				value: '1',
			},
			{
				operatorName: 'contains',
				propertyName: 'attribute/2',
				value: '',
			},
			{
				operatorName: 'gt',
				propertyName: 'day',
				value: 'last24Hours',
			},
		],
	},
	{key: 'operator', value: 'gt'},
	{key: 'value', value: 1},
]);

describe('EventInput', () => {
	let handleWindowError;

	beforeEach(() => {
		pedantic.disable();

		handleWindowError = (event) => {
			if (
				event.message &&
				event.message.includes('Cannot add property _key')
			) {
				event.preventDefault();
			}
		};

		window.addEventListener('error', handleWindowError);
	});

	afterEach(() => {
		pedantic.enable();

		window.removeEventListener('error', handleWindowError);
	});

	it('renders', async () => {
		const mocks = [
			mockEventPropertiesReq(
				range(10).map((i) =>
					data.mockEventAttributeDefinition(i, {
						__typename: 'EventProperty',
					})
				),
				{
					eventId: '3',
					page: 0,
					size: 25,
					sort: {
						column: 'name',
						type: 'ASC',
					},
				}
			),
		];

		render(
			<MockedProvider
				addTypename={false}
				cache={
					new InMemoryCache({
						addTypename: false,
						freezeResults: false,
					})
				}
				mocks={mocks}
			>
				<MemoryRouter
					initialEntries={[
						'/workspace/23/123123/contacts/segments/create?type=BATCH',
					]}
				>
					<Route path={Routes.CONTACTS_SEGMENT_CREATE}>
						<EventInput
							displayValue="Asset Clicked"
							onChange={jest.fn()}
							operatorRenderer={() => <div>has / has not</div>}
							property={
								new Property({
									entityName: 'Event',
									id: '3',
									label: 'assetDepthReached',
									name: '3',
									options: [],
									propertyKey: 'event',
									type: 'event',
								})
							}
							segmentType="BATCH"
							touched={{
								attribute: true,
								attributeValue: 'true',
								dateFilter: true,
								occurenceCount: true,
							}}
							valid={{
								attribute: true,
								attributeValue: 'true',
								dateFilter: true,
								occurenceCount: true,
							}}
							value={fromJS(mockValue)}
						/>
					</Route>
				</MemoryRouter>
			</MockedProvider>
		);

		await waitFor(() =>
			expect(document.body.querySelector('.loading-root')).toBeNull()
		);

		expect(screen.getByText('has / has not')).toBeInTheDocument();
		expect(screen.getByText('Asset Clicked')).toBeInTheDocument();
	}, 10000);
});
