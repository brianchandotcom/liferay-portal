/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import Form from '../index';

jest.unmock('react-dom');

describe('Form', () => {
	it('renders a Form', () => {
		const {container} = render(<Form />);

		expect(container).toMatchSnapshot();
	});

	it('renders a Form Group', () => {
		const {container} = render(<Form.Group />);

		expect(container).toMatchSnapshot();
	});

	it('renders an autofit Form Group', () => {
		const {container} = render(<Form.Group autoFit />);

		expect(container.querySelector('.form-group-autofit')).toBeTruthy();
	});
});
