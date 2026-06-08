/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {CriteriaSidebarItem} from '../CriteriaSidebarItem';

const connectDnd = jest.fn((element) => element);

jest.unmock('react-dom');

describe('CriteriaSidebarItem', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<CriteriaSidebarItem
				connectDragSource={connectDnd}
				label="Page Views"
				propertyKey="user"
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
