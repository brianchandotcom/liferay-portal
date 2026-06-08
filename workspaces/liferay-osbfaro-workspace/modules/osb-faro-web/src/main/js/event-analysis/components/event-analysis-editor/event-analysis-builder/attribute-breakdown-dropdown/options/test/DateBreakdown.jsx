/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import DateBreakdown from '../DateBreakdown';

jest.unmock('react-dom');

describe('DateBreakdown', () => {
	it('renders', () => {
		const {container} = render(<DateBreakdown onSubmit={jest.fn()} />);

		expect(container).toMatchSnapshot();
	});
});
