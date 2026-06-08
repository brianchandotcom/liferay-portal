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

import AttributeChip, {DragTypes} from '../AttributeChip';

jest.unmock('react-dom');

describe('AttributeChip', () => {
	it('render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<DndProvider backend={HTML5Backend}>
						<AttributeChip
							dataType="STRING"
							dragType={DragTypes.AttributeBreakdownChip}
							id="0"
							index={1}
							label="Event"
							onCloseClick={jest.fn()}
							onMove={jest.fn()}
							value="Article Title"
						/>
					</DndProvider>
				</Provider>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});
});
