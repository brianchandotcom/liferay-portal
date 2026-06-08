/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import StagedSubnav from '../StagedSubnav';

jest.unmock('react-dom');

const DefaultComponent = (props) => (
	<StagedSubnav
		viewCurrentLinkText="view current items"
		viewStagedLinkText="view added items"
		{...props}
	/>
);

describe('StagedSubnav', () => {
	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('renders with the showStaged active labels', () => {
		const {getByText} = render(<DefaultComponent showStaged />);

		expect(getByText('Showing only selected items.')).toBeTruthy();
	});
});
