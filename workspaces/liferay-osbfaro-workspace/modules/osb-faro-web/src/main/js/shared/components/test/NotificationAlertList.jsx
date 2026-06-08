/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import {range} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import * as API from '~/shared/api';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';

import NotificationAlertList from '../NotificationAlertList';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useTimeZone', () => ({
	useTimeZone: () => ({
		displayTimeZone: 'UTC -03:00 Brasilia Time (America/Recife)',
	}),
}));

const defaultProps = {
	data: range(1).map((i) => data.mockNotification(i)),
	groupId: '23',
	loading: false,
	refetch: () => {},
};

describe('NotificationAlertList', () => {
	API.notifications.fetchNotifications.mockReturnValue(
		Promise.resolve(range(1).map((i) => data.mockNotification(i)))
	);

	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<NotificationAlertList {...defaultProps} />
				</StaticRouter>
			</Provider>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('hides notification when click on close button', () => {
		const {container, queryByText} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<NotificationAlertList {...defaultProps} />
				</StaticRouter>
			</Provider>
		);

		jest.runAllTimers();

		fireEvent.click(container.querySelector('.close'));

		expect(
			queryByText('Workspace timezone has changed to as of today')
		).toBeNull();
	});
});
