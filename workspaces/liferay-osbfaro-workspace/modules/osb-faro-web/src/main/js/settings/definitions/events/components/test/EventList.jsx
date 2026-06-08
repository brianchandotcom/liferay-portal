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
import * as data from '~/test/data';
import {mockEventDefinitionsReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import EventList from '../EventList';

jest.unmock('react-dom');

const DefaultComponent = ({event, ...otherProps}) => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={[
				'/workspace/23/settings/definitions/events/default?delta=1',
			]}
		>
			<Route path={Routes.SETTINGS_DEFINITIONS_EVENTS}>
				<MockedProvider
					cache={
						new InMemoryCache({
							addTypename: false,
							freezeResults: false,
						})
					}
					mocks={[
						mockEventDefinitionsReq([
							data.mockEventDefinition(0, {
								__typename: 'EventDefinition',
								...event,
							}),
						]),
					]}
				>
					<EventList groupId="23" {...otherProps} />
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('EventList', () => {
	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders hide/unhide icon and not move to the right side border of the table when row is clicked', async () => {
		const {container} = render(
			<DefaultComponent
				event={{
					hidden: true,
				}}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const firstTr = container.querySelector('.clickable');

		fireEvent.click(firstTr);

		expect(firstTr.querySelector('.custom-control-input').checked).toBe(
			true
		);

		expect(firstTr.querySelector('.row-actions')).toBeTruthy();
	});
});
