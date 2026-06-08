/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {fireEvent, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {Routes} from '~/shared/util/router';
import mockStore from '~/test/mock-store';

import {AttributesContext} from '../../context/attributes';
import EventSection from '../EventSection';

jest.unmock('react-dom');

const DefaultComponent = ({attributesValue, ...props}) => (
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
					{attributesValue ? (
						<AttributesContext.Provider value={attributesValue}>
							<EventSection {...props} />
						</AttributesContext.Provider>
					) : (
						<EventSection {...props} />
					)}
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('EventSection', () => {
	it('render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('render with event', () => {
		const {container} = render(
			<DefaultComponent event={{name: 'View Article'}} />
		);

		expect(container).toMatchSnapshot();
	});

	it('clears the event and the attributes when the remove button is clicked', () => {
		const deleteAllAttributes = jest.fn();
		const onEventChange = jest.fn();

		const {container} = render(
			<DefaultComponent
				attributesValue={{
					attributes: {},
					breakdownOrder: [],
					breakdowns: {},
					changed: false,
					deleteAllAttributes,
					filterOrder: [],
					filters: {},
				}}
				event={{id: '1', name: 'View Article'}}
				onEventChange={onEventChange}
			/>
		);

		fireEvent.click(container.querySelector('.remove-button'));

		expect(onEventChange).toHaveBeenCalledTimes(1);
		expect(onEventChange).toHaveBeenCalledWith(null);
		expect(deleteAllAttributes).toHaveBeenCalledTimes(1);
	});
});
