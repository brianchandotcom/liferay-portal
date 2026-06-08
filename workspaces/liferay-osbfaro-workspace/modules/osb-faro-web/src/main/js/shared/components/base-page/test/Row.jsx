/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Row from '../Row';

jest.unmock('react-dom');

describe('BasePage.Row', () => {
	afterEach(cleanup);

	it('renders Row', () => {
		const {container} = render(<Row>Test Test</Row>);

		expect(container).toMatchSnapshot();
	});
});
