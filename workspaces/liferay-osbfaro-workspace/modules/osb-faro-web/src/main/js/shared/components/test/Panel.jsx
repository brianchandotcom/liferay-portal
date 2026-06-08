/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Panel from '../Panel';

jest.unmock('react-dom');

describe('Panel', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<Panel title="Panel Title" />);
		expect(container).toMatchSnapshot();
	});

	it('does not render a panel body when expandable is true and the expanded state is false', () => {
		const {queryByText} = render(
			<Panel expandable title="Panel Title">
				Foo Panel Body
			</Panel>
		);

		expect(queryByText('Foo Panel Body')).toBeNull();
	});

	it('renders a panel body when expandable is true and initialExpanded prop is true', () => {
		const {queryByText} = render(
			<Panel expandable initialExpanded title="Panel Title">
				Foo Panel Body
			</Panel>
		);

		expect(queryByText('Foo Panel Body')).toBeTruthy();
	});
});
