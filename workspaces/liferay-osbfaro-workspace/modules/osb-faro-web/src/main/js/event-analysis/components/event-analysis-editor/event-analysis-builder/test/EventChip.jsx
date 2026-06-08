/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {fireEvent, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import client from '~/shared/apollo/client';
import mockStore from '~/test/mock-store';

import EventChip from '../EventChip';

jest.unmock('react-dom');

describe('EventChip', () => {
	it('render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<EventChip event={{name: 'View Article'}} />
				</Provider>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});

	it('calls onEventChange with null when the remove button is clicked', () => {
		const onEventChange = jest.fn();

		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<EventChip
						event={{id: '1', name: 'View Article'}}
						onEventChange={onEventChange}
					/>
				</Provider>
			</ApolloProvider>
		);

		fireEvent.click(container.querySelector('.remove-button'));

		expect(onEventChange).toHaveBeenCalledTimes(1);
		expect(onEventChange).toHaveBeenCalledWith(null);
	});
});
