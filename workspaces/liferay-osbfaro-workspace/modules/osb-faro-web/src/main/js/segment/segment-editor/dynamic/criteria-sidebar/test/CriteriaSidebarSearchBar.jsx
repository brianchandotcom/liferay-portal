/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import CriteriaSidebarSearchBar from '../CriteriaSidebarSearchBar';

jest.unmock('react-dom');

describe('CriteriaSidebarSearchBar', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<CriteriaSidebarSearchBar onChange={jest.fn()} />
		);

		expect(container).toMatchSnapshot();
	});

	it('renders w/ searchValue', () => {
		const searchValue = 'Page Views';

		const {getByDisplayValue} = render(
			<CriteriaSidebarSearchBar
				onChange={jest.fn()}
				searchValue={searchValue}
			/>
		);

		expect(getByDisplayValue(searchValue)).toBeTruthy();
	});
});
