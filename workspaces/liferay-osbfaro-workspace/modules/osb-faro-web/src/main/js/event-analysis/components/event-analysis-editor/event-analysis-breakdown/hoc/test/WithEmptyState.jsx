/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import withEmptyState from '../WithEmptyState';

jest.unmock('react-dom');

describe('withEmptyState', () => {
	it('renders with an empty state', () => {
		const WrappedComponent = withEmptyState(() => 'test');

		const {container} = render(<WrappedComponent />);

		expect(container.querySelector('.breakdown-empty')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('returns the passed component', () => {
		const componentSpy = jest.fn(() => 'test');

		const WrappedComponent = withEmptyState(componentSpy);

		render(<WrappedComponent event={{name: 'Test Event'}} />);

		expect(componentSpy).toHaveBeenCalled();
	});
});
