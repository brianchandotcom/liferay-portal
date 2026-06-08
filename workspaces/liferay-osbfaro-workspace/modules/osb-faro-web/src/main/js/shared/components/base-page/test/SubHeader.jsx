/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import SubHeader from '../SubHeader';

jest.unmock('react-dom');

describe('BasePage.SubHeader', () => {
	afterEach(cleanup);

	it('renders SubHeader', () => {
		const {container} = render(<SubHeader>Test Test</SubHeader>);

		expect(container).toMatchSnapshot();
	});
});
