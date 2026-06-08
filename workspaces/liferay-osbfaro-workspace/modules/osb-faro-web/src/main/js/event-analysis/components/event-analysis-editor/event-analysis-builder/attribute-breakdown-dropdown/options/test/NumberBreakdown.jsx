/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import NumberBreakdown from '../NumberBreakdown';

jest.unmock('react-dom');

describe('NumberBreakdown', () => {
	it('renders', () => {
		const {container} = render(<NumberBreakdown onSubmit={jest.fn()} />);

		expect(container).toMatchSnapshot();
	});
});
