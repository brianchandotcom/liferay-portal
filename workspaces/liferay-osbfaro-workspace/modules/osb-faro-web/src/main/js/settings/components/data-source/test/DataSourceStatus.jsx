/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import DataSourceStatus from '../DataSourceStatus';

jest.unmock('react-dom');

describe('DataSourceStatus', () => {
	it('renders', () => {
		const {container} = render(
			<DataSourceStatus display="info" label="foo" message="bar" />
		);

		expect(container).toMatchSnapshot();
	});
});
