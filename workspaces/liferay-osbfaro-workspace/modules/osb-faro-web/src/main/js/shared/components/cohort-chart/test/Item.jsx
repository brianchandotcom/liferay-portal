/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import Item from '../Item';

jest.unmock('react-dom');

describe('CohortChartItem', () => {
	it('renders', () => {
		const {container} = render(
			<Item
				colorHex="#000000"
				date="February 20, 2010"
				dateLabelFn={(date) => date}
				period="Day 3"
				retention={36.21231231231}
				value={123}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
