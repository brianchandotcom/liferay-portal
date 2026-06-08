/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {Routes} from '~/shared/util/router';
import mockStore from '~/test/mock-store';

import {AttributeBreakdownSection} from '../AttributeBreakdownSection';

jest.unmock('react-dom');

const WrappedComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/workspace/23/event-analysis']}>
			<Route path={Routes.EVENT_ANALYSIS}>
				<MockedProvider
					cache={
						new InMemoryCache({
							addTypename: false,
							freezeResults: false,
						})
					}
				>
					<DndProvider backend={HTML5Backend}>
						<AttributeBreakdownSection
							attributes={[]}
							breakdownOrder={[]}
							breakdowns={[]}
							{...props}
						/>
					</DndProvider>
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('AttributeBreakdownSection', () => {
	it('renders', () => {
		const {container} = render(<WrappedComponent />);

		expect(container.querySelector('.add-attribute')).toBeNull();
		expect(container).toMatchSnapshot();
	});

	it('renders w/ add breakdown button', () => {
		const {container} = render(<WrappedComponent eventId="1" />);

		expect(container.querySelector('.add-attribute')).toBeTruthy();
	});

	it('renders w/o add breakdown button if 5 breakdowns exists', () => {
		const {container} = render(
			<WrappedComponent
				attributes={{
					1: {
						dataType: 'STRING',
						displayName: 'Title',
						id: '1',
						name: 'title',
					},
					400: {
						dataType: 'STRING',
						displayName: 'Author',
						id: '400',
						name: 'author',
					},
					500: {
						dataType: 'STRING',
						displayName: 'Date',
						id: '500',
						name: 'date',
					},

					123123: {
						dataType: 'STRING',
						displayName: 'Job Title',
						id: '123123',
						name: 'jobTitle',
					},
					321321: {
						dataType: 'STRING',
						displayName: 'Article Title',
						id: '321321',
						name: 'articleTitle',
					},
				}}
				breakdownOrder={['1', '321321', '123123', '400', '500']}
				breakdowns={{
					1: {
						attributeId: '1',
						dataType: 'STRING',
						type: 'event',
					},
					400: {
						attributeId: '400',
						dataType: 'STRING',
						type: 'event',
					},
					500: {
						attributeId: '500',
						dataType: 'STRING',
						type: 'event',
					},

					123123: {
						attributeId: '123123',
						dataType: 'STRING',
						type: 'event',
					},
					321321: {
						attributeId: '321321',
						dataType: 'STRING',
						type: 'event',
					},
				}}
				eventId="2"
			/>
		);

		expect(container.querySelector('.add-attribute')).toBeNull();
	});

	it('renders w/ breakdowns', () => {
		const {container} = render(
			<WrappedComponent
				attributes={{
					123123: {
						displayName: 'Job Title',
						id: '123123',
						name: 'jobTitle',
					},
					321321: {
						displayName: 'Article Title',
						id: '321321',
						name: 'articleTitle',
					},
				}}
				breakdownOrder={['321321', '123123']}
				breakdowns={{
					123123: {
						attributeId: '123123',
						dataType: 'STRING',
						type: 'event',
					},
					321321: {
						attributeId: '321321',
						dataType: 'STRING',
						type: 'event',
					},
				}}
				eventId="2"
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
