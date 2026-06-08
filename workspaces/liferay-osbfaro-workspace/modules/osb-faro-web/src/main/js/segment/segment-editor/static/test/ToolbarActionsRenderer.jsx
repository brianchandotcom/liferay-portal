/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {OrderedMap} from 'immutable';
import React from 'react';

import ToolbarActionsRenderer from '../ToolbarActionsRenderer';

jest.unmock('react-dom');

describe('ToolbarActionsRenderer', () => {
	it('renders', () => {
		const {container} = render(<ToolbarActionsRenderer />);

		expect(container).toMatchSnapshot();
	});

	it('renders secondary button actions if items are selected', () => {
		const {container} = render(
			<ToolbarActionsRenderer
				selectedItemsIOMap={
					new OrderedMap([
						[1, {}],
						[2, {}],
					])
				}
			/>
		);

		expect(container.querySelectorAll('button')).toHaveLength(2);
	});

	it('does not render remove button when showAdded is true', () => {
		const {container} = render(
			<ToolbarActionsRenderer
				selectedItemsIOMap={
					new OrderedMap([
						[1, {}],
						[2, {}],
					])
				}
				showAdded
			/>
		);

		expect(container.querySelectorAll('button')).toHaveLength(1);
		expect(container.querySelector('button')).toHaveTextContent(
			'Undo Changes'
		);
	});
});
