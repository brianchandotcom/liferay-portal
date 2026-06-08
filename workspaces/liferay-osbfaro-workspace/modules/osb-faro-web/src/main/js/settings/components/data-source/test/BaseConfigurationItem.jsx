/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import BaseConfigurationItem from '../BaseConfigurationItem';

jest.unmock('react-dom');

describe('BaseConfigurationItem', () => {
	it('renders', () => {
		const {container} = render(
			<BaseConfigurationItem
				description="Test description"
				title="Test Test"
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders as disabled', () => {
		const {getByText} = render(
			<BaseConfigurationItem
				buttonParams={{disabled: true}}
				description="Test description"
				title="Test Test"
			/>
		);

		expect(getByText('Configure')).toHaveClass('link-disabled');
	});

	it('renders with a status message', () => {
		const {getByText} = render(
			<BaseConfigurationItem
				buttonParams={{disabled: true}}
				description="Test description"
				statusMessage="Test Status Message"
				title="Test Test"
			/>
		);

		expect(getByText('Test Status Message')).toBeTruthy();
	});

	it('renders with a metric bar', () => {
		const {container} = render(
			<BaseConfigurationItem
				buttonParams={{disabled: true}}
				completion={0.8}
				description="Test description"
				showBar
				statusMessage="Test Status Message"
				title="Test Test"
			/>
		);

		expect(container.querySelector('.metric-bar-root')).toBeTruthy();
		expect(container.querySelector('.bar')).toHaveStyle('width: 80%;');
	});
});
