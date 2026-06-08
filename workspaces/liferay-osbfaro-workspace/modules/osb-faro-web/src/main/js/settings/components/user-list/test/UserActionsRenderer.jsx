/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {User} from '~/shared/util/records';

import UserActionsRenderer from '../UserActionsRenderer';

jest.unmock('react-dom');

describe('UserActionsRenderer', () => {
	it('renders', () => {
		const {container} = render(
			<UserActionsRenderer currentUserId={1} data={new User({id: 2})} />
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with save and cancel buttons because editing is true', () => {
		const {findByText} = render(
			<UserActionsRenderer
				currentUserId={1}
				data={new User({id: 2})}
				editing
			/>
		);

		expect(findByText('Save')).toBeTruthy();
		expect(findByText('Cancel')).toBeTruthy();
	});

	it('renders without row actions for the current user', () => {
		const {queryAllByRole} = render(
			<UserActionsRenderer
				currentUserId={1}
				data={new User({id: 1})}
				editing
			/>
		);

		expect(queryAllByRole('button').length).toBe(0);
	});
});
