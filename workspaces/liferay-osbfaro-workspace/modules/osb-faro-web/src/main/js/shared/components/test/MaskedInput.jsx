/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import MaskedInput from '../MaskedInput';

jest.unmock('react-dom');

describe('MaskedInput', () => {
	afterEach(cleanup);

	it('renders input', () => {
		const {container} = render(<MaskedInput mask={[]} />);

		expect(container).toMatchSnapshot();
	});

	it('renders with mask as a function', () => {
		const maskFn = (rawValue) => {
			rawValue = rawValue.replace('NUMBER', '');
			rawValue = rawValue.replace('STRING', '');

			const isNumber = !isNaN(Number(rawValue));

			return isNumber ? 'NUMBER'.split('') : 'STRING'.split('');
		};
		const defaultProps = {mask: maskFn, showMask: true, value: 'test'};

		const {container, rerender} = render(<MaskedInput {...defaultProps} />);

		expect(container.querySelector('input')).toHaveAttribute(
			'value',
			'test'
		);

		rerender(<MaskedInput {...defaultProps} value={123} />);
		jest.runAllTimers();
		expect(container.querySelector('input')).toHaveAttribute(
			'value',
			'123'
		);
	});

	it('renders with mask showing', () => {
		const {container} = render(
			<MaskedInput mask={[/\d/, /\d/, /\d/]} showMask />
		);
		expect(container).toMatchSnapshot();
	});

	it('succeeds with a specific mask', () => {
		const {container} = render(
			<MaskedInput mask={[/\d/, /\d/, /\d/]} value={123} />
		);
		expect(container).toMatchSnapshot();
	});
});
