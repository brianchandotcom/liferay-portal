/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {Routes} from '~/shared/util/router';
import mockStore from '~/test/mock-store';

import {AttributeFilterSection} from '../AttributeFilterSection';

jest.unmock('react-dom');

const WrappedComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/workspace/23/event-analysis']}>
			<Route path={Routes.EVENT_ANALYSIS}>
				<MockedProvider freezeResults={false}>
					<DndProvider backend={HTML5Backend}>
						<AttributeFilterSection
							attributes={[]}
							filterOrder={[]}
							filters={[]}
							{...props}
						/>
					</DndProvider>
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('AttributeFilterSection', () => {
	it('renders', () => {
		const {container} = render(<WrappedComponent />);

		expect(container.querySelector('.add-attribute')).toBeNull();
		expect(container).toMatchSnapshot();
	});

	it('renders w/ add attribute button', () => {
		const {container} = render(<WrappedComponent eventId="1" />);

		expect(container.querySelector('.add-attribute')).toBeTruthy();
	});

	it('renders w/ filter', () => {
		const {container} = render(
			<WrappedComponent
				attributes={{
					123123: {
						dataType: 'STRING',
						displayName: 'Job Title',
						id: '123123',
						name: 'jobTitle',
					},
				}}
				eventId="1"
				filterOrder={['123123']}
				filters={{
					123123: {
						attributeId: '123123',
						dataType: 'STRING',
						operator: 'eq',
						type: 'event',
						value: ['Stuff'],
					},
				}}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
