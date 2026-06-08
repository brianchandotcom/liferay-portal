/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import BaseConfigurationOverview from '../BaseConfigurationOverview';

jest.unmock('react-dom');

describe('BaseConfigurationOverview', () => {
	it('renders', () => {
		const mockConfigurationItems = [
			{
				description: 'foo description',
				label: 'edit',
				title: 'foo title',
			},
		];

		const {container, queryByText} = render(
			<BaseConfigurationOverview
				configurationItems={mockConfigurationItems}
			/>
		);

		expect(queryByText('foo title')).toBeTruthy();
		expect(queryByText('foo description')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});
});
