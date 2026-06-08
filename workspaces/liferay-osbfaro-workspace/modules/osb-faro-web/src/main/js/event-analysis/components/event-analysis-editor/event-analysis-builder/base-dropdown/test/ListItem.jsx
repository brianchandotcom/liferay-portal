/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import ListItem from '../ListItem';

jest.unmock('react-dom');

describe('BaseDropdownListItem', () => {
	it('renders', () => {
		const {container} = render(
			<ListItem
				item={{
					displayName: 'Test Display Name',
					id: '0',
					name: 'testName',
					type: 'custom',
				}}
				onClick={jest.fn()}
				onEditClick={jest.fn()}
			/>
		);

		expect(container.querySelector('.active')).toBeNull();
		expect(container.querySelector('.disabled')).toBeNull();
		expect(container.querySelector('.options-button')).toBeNull();
		expect(container).toMatchSnapshot();
	});

	it('renders with an Attribute', () => {
		const {container} = render(
			<ListItem
				item={{
					dataType: 'string',
					displayName: 'Filed Ticket',
					id: '4',
					name: 'filedTicket',
				}}
				onClick={jest.fn()}
				onEditClick={jest.fn()}
				onOptionsClick={jest.fn()}
			/>
		);

		expect(container.querySelector('.options-button')).toBeTruthy();
	});

	it('renders as disabled', () => {
		const {container} = render(
			<ListItem
				disabled
				item={{
					dataType: 'string',
					displayName: 'Filed Ticket',
					id: '4',
					name: 'filedTicket',
				}}
				onClick={jest.fn()}
				onEditClick={jest.fn()}
				onOptionsClick={jest.fn()}
			/>
		);

		expect(container.querySelector('.disabled')).toBeTruthy();
	});

	it('renders as active', () => {
		const {container} = render(
			<ListItem
				active
				item={{
					dataType: 'string',
					displayName: 'Filed Ticket',
					id: '4',
					name: 'filedTicket',
				}}
				onClick={jest.fn()}
				onEditClick={jest.fn()}
				onOptionsClick={jest.fn()}
			/>
		);

		expect(container.querySelector('.active')).toBeTruthy();
	});
});
