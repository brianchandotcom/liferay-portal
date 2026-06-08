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

import EventAnalysisBuilder from '../index';

jest.unmock('react-dom');

const WrappedComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/workspace/23/event-analysis']}>
			<Route path={Routes.EVENT_ANALYSIS}>
				<MockedProvider freezeResults={false}>
					<DndProvider backend={HTML5Backend}>
						<EventAnalysisBuilder {...props} />
					</DndProvider>
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('Event Analysis Builder', () => {
	it('render', () => {
		const {container} = render(<WrappedComponent />);

		expect(container).toMatchSnapshot();
	});

	it('render with filters & breakdowns', () => {
		const {container} = render(
			<WrappedComponent
				attributes={[
					{
						id: '321321',
						name: 'Article Title',
					},
					{
						id: '123123',
						name: 'Job Title',
					},
				]}
				breakdowns={[
					{
						attributeId: '321321',
						dataType: 'string',
						type: 'event',
					},
					{
						attributeId: '123123',
						dataType: 'string',
						type: 'event',
					},
				]}
				event={{
					id: '123123',
					name: 'Article Views',
					type: 'custom',
				}}
				filters={[
					{
						attributeId: '123123',
						operator: 'eq',
						value: ['Stuff'],
					},
				]}
				onEventChange={jest.fn()}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
