/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import withSheet from '../WithSheet';

jest.unmock('react-dom');

describe('withSheet', () => {
	it('renders', () => {
		const WrappedComponent = withSheet({large: true})(() => (
			<p>Test Test</p>
		));

		const {container} = render(<WrappedComponent />);

		expect(container).toMatchSnapshot();
	});
});
