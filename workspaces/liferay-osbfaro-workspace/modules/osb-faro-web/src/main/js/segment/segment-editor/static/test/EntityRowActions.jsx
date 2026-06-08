/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {Set} from 'immutable';
import React from 'react';

import EntityRowActions from '../EntityRowActions';

jest.unmock('react-dom');

describe('EntityRowActions', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<EntityRowActions />);
		expect(container).toMatchSnapshot();
	});

	it('renders with an added label and undo link', () => {
		const {container} = render(
			<EntityRowActions
				addIdsISet={new Set([12, 14])}
				data={{id: 12}}
				showAdded
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with a removed label and undo link', () => {
		const {container} = render(
			<EntityRowActions
				data={{id: 12}}
				removeIdsISet={new Set([12, 14])}
			/>
		);
		expect(container).toMatchSnapshot();
	});

	it('renders nothing if items are selected but it is not part of added/removed', () => {
		const {container} = render(
			<EntityRowActions data={{id: 22}} itemsSelected />
		);

		expect(container).toBeTruthy();
	});
});
