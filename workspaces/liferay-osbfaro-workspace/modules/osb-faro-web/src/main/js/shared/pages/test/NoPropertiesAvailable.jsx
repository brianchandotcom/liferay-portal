import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {open} from '~/shared/actions/modals';
import * as API from '~/shared/api';
import {User} from '~/shared/util/records';
import {getImmutableMock, mockMemberUser, mockUser} from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import NoPropertiesAvailable from '../NoPropertiesAvailable';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

jest.mock('~/shared/actions/modals', () => ({
	actionTypes: {},
	close: jest.fn(),
	modalTypes: {},
	open: jest.fn(() => ({meta: {}, payload: {}, type: 'open'})),
}));

jest.unmock('react-dom');

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<StaticRouter>
			<NoPropertiesAvailable
				currentUser={getImmutableMock(User, mockUser)}
				groupId="123123"
				{...props}
			/>
		</StaticRouter>
	</Provider>
);

describe('NoPropertiesAvailable', () => {
	afterEach(cleanup);

	it('renders', async () => {
		API.dataSource.search.mockReturnValueOnce(Promise.resolve({total: 0}));

		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('does not display the "Start" button to trigger onboarding if the user is not an AC admin', () => {
		const {queryByRole} = render(
			<DefaultComponent
				currentUser={getImmutableMock(User, mockMemberUser)}
			/>
		);

		jest.runAllTimers();

		expect(queryByRole('button')).toBeNull();
	});

	it('calls open on "Start" click', async () => {
		API.dataSource.search.mockReturnValueOnce(Promise.resolve({total: 0}));

		const {container, queryByText} = render(
			<DefaultComponent open={open} />
		);

		expect(open).not.toBeCalled();

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		fireEvent.click(queryByText('Start'));

		expect(open).toBeCalled();
	});
});
