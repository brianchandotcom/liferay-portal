/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import ItemText from '../ItemText';

jest.unmock('react-dom');

const DefaultComponent = (props) => <ItemText {...props}>Content</ItemText>;

describe('ItemText', () => {
	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('renders with subtext', () => {
		const {getByText} = render(<DefaultComponent subtext="subtext here" />);

		expect(getByText('Content')).toHaveClass('list-group-subtext');
	});
});
