/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import RoleRenderer from '../RoleRenderer';

jest.unmock('react-dom');

const userRoleOptions = [
	{label: 'Administrator', value: 'Site Administrator'},
	{label: 'Member', value: 'Site Member'},
	{label: 'Owner', value: 'Site Owner'},
];

describe('RoleRenderer', () => {
	it('renders', () => {
		const {container} = render(
			<RoleRenderer data={{roleName: 'Site Owner'}} />
		);

		expect(container).toMatchSnapshot();
	});

	it('renders as being edited', () => {
		const {container} = render(
			<RoleRenderer
				data={{roleName: 'Site Member'}}
				editing
				options={userRoleOptions}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders and call the onUpdateEdits prop callback with the initial roleName', () => {
		const onUpdateEditsSpy = jest.fn();

		render(
			<RoleRenderer
				data={{roleName: 'Site Member'}}
				editing
				onUpdateEdits={onUpdateEditsSpy}
				options={userRoleOptions}
			/>
		);

		expect(onUpdateEditsSpy).toHaveBeenCalledWith(
			'roleName',
			'Site Member'
		);
	});
});
