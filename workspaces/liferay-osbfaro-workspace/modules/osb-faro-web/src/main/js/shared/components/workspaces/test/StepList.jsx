/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import StepList from '../StepList';

jest.unmock('react-dom');

const DefaultComponent = (props) => (
	<StepList steps={['step1', 'step2']} {...props} />
);

describe('StepList', () => {
	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('renders with secondaryInfo and steps', () => {
		const {getByText} = render(<DefaultComponent secondaryInfo="test" />);

		expect(getByText('test')).toBeTruthy();
	});

	it('renders without bullet', () => {
		const {getByText} = render(
			<DefaultComponent hideBullets secondaryInfo="test" />
		);

		expect(getByText('test').parentElement).toHaveClass('hide-bullets');
	});
});
