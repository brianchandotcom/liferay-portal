/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';
import {User} from '~/shared/util/records';

import {WorkspacesBasePage} from '../BasePage';

jest.unmock('react-dom');

const currentUser = new User({
	emailAddress: 'test@test.com',
	name: 'Test Test',
});

const DefaultComponent = (props) => (
	<StaticRouter>
		<WorkspacesBasePage
			currentUser={currentUser}
			title="Test Title"
			{...props}
		/>
	</StaticRouter>
);

describe('WorkspacesBasePage', () => {
	it('renders', () => {
		const {container} = render(
			<DefaultComponent
				details={[
					<p key="1">Test Details</p>,
					<p key="2">More Test Details</p>,
				]}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders when details is jsx', () => {
		const {queryByText} = render(
			<DefaultComponent details={<b>Test0</b>} />
		);

		expect(queryByText('Test0')).toBeTruthy();
	});

	it('renders when details is a string', () => {
		const {queryByText} = render(
			<DefaultComponent details="Test Details" />
		);

		expect(queryByText('Test Details')).toBeTruthy();
	});

	it('renders with back button', () => {
		const {queryByText} = render(
			<DefaultComponent
				backLabel="Back to Test"
				backURL="#"
				details={['Test Details. ', 'More Test Details']}
			/>
		);

		expect(queryByText('Back to Test')).toBeTruthy();
	});
});
