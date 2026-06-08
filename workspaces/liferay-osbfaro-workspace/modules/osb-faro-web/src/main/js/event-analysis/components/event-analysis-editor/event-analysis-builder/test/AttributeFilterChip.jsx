/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {render} from '@testing-library/react';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {Provider} from 'react-redux';
import client from '~/shared/apollo/client';
import mockStore from '~/test/mock-store';

import AttributeFilterChip from '../AttributeFilterChip';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456',
	}),
}));

describe('AttributeFilterChip', () => {
	it('render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<DndProvider backend={HTML5Backend}>
						<AttributeFilterChip
							attribute={{
								dataType: 'STRING',
								displayName: 'Article View',
								id: '0',
								name: 'articleView',
							}}
							filter={{
								attributeId: '0',
								dataType: 'STRING',
								operator: 'eq',
								type: 'event',
								value: ['Stuff'],
							}}
							index={1}
						/>
					</DndProvider>
				</Provider>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});
});
