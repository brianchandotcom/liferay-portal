/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render, waitFor} from '@testing-library/react';
import {range} from 'lodash';
import React from 'react';
import {Provider, connect} from 'react-redux';
import {close, open} from '~/shared/actions/modals';
import * as API from '~/shared/api';
import ModalRenderer from '~/shared/components/ModalRenderer';
import {useModalNotifications} from '~/shared/hooks/useModalNotifications';
import {
	NotificationSubtypes,
	NotificationTypes,
} from '~/shared/util/records/Notification';
import * as data from '~/test/data';
import {mockGetDateNow} from '~/test/mock-date';
import mockStore from '~/test/mock-store';

jest.unmock('react-dom');

jest.mock('~/shared/components/modals/NewRequestModal', () => () => null);

const WrapperComponent = connect(null, {close, open})(({close, open}) => {
	useModalNotifications(close, '23', open);

	return (
		<div>
			<span>testing</span>
		</div>
	);
});

describe('useModalNotifications', () => {
	it('opens a notification modal', async () => {
		mockGetDateNow(data.getTimestamp(0));

		API.notifications.fetchNotifications.mockReturnValueOnce(
			Promise.resolve(
				range(1).map((i) =>
					data.mockNotification(i, {
						subtype: NotificationSubtypes.TimeZoneAdmin,
						type: NotificationTypes.Modal,
					})
				)
			)
		);

		const {getByText} = render(
			<Provider store={mockStore()}>
				<ModalRenderer />
				<WrapperComponent />
			</Provider>
		);

		jest.runAllTimers();

		await waitFor(() => getByText('Set Timezone'));

		expect(getByText('Set Timezone')).toBeInTheDocument();
	});

	it('opens another notification modal after closing one when having multiple modals', async () => {
		mockGetDateNow(data.getTimestamp(0));

		API.notifications.fetchNotifications.mockReturnValue(
			Promise.resolve(
				range(2).map((i) =>
					data.mockNotification(i, {
						subtype: NotificationSubtypes.TimeZoneAdmin,
						type: NotificationTypes.Modal,
					})
				)
			)
		);

		const {getByText} = render(
			<Provider store={mockStore()}>
				<ModalRenderer />
				<WrapperComponent />
			</Provider>
		);

		jest.runAllTimers();

		await waitFor(() => getByText('Do This Later'));

		fireEvent.click(getByText('Do This Later'));

		expect(getByText('Set Timezone')).toBeTruthy();
	});
});
