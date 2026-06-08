/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import InvitePeople from '../InvitePeople';

jest.unmock('react-dom');

const inviteSentMessage =
	'You can see the new members invitation status and role permissions under user management in settings.';

describe('InvitePeople', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<InvitePeople onClose={noop} onNext={noop} />
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders the connected state when invitations have been sent', async () => {
		const {container, queryByPlaceholderText, queryByText} = render(
			<Provider store={mockStore()}>
				<InvitePeople dxpConnected onClose={noop} onNext={noop} />
			</Provider>
		);

		expect(queryByText(inviteSentMessage)).toBeNull();

		fireEvent.change(queryByPlaceholderText('Enter Email Address'), {
			target: {
				value: 'test@liferay.com',
			},
		});

		fireEvent.click(queryByText('Send Invitations'));

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(queryByText(inviteSentMessage)).not.toBeNull();
		expect(queryByText('Next')).not.toBeNull();
	});

	it('renders the "Done" button invitations have been sent without connecting dxp', async () => {
		const {container, queryByPlaceholderText, queryByText} = render(
			<Provider store={mockStore()}>
				<InvitePeople
					dxpConnected={false}
					onClose={noop}
					onNext={noop}
				/>
			</Provider>
		);

		expect(queryByText(inviteSentMessage)).toBeNull();

		fireEvent.change(queryByPlaceholderText('Enter Email Address'), {
			target: {
				value: 'test@liferay.com',
			},
		});

		fireEvent.click(queryByText('Send Invitations'));

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(queryByText(inviteSentMessage)).not.toBeNull();
		expect(queryByText('Done')).not.toBeNull();
	});
});
