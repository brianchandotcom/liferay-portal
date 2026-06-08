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

import AttributeBreakdownChip from '../AttributeBreakdownChip';

jest.unmock('react-dom');

describe('AttributeBreakdownChip', () => {
	it('render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<DndProvider backend={HTML5Backend}>
						<AttributeBreakdownChip
							attribute={{
								dataType: 'STRING',
								displayName: 'Article View',
								id: '0',
								name: 'articleView',
							}}
							breakdown={{
								attributeId: '0',
								dataType: 'STRING',
								type: 'event',
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
