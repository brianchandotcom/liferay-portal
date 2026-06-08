/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';

import {TitleEditor} from '../TitleEditor';

jest.unmock('react-dom');

describe('TitleEditor', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<TitleEditor name="test" onChange={noop} />);
		expect(container).toMatchSnapshot();
	});

	it('renders with placeholder', () => {
		const placeholder = 'test placeholder';

		const {queryByPlaceholderText} = render(
			<TitleEditor
				name="test"
				onChange={noop}
				placeholder={placeholder}
			/>
		);

		expect(queryByPlaceholderText(placeholder)).toBeTruthy();
	});

	it('renders with value', () => {
		const value = 'foo';

		const {queryByText} = render(
			<TitleEditor name="test" onChange={noop} value={value} />
		);

		expect(queryByText(value)).toBeTruthy();
	});

	it('renders as editing', () => {
		const placeholder = 'test placeholder';

		const {container, getByPlaceholderText} = render(
			<TitleEditor
				name="test"
				onChange={noop}
				placeholder={placeholder}
			/>
		);

		const input = getByPlaceholderText(placeholder);

		expect(input).toHaveClass('hide');

		fireEvent.click(container.querySelector('.btn'));

		expect(input).not.toHaveClass('hide');
	});

	it('does not be editable if editable prop is false', () => {
		const {container} = render(
			<TitleEditor editable={false} name="test" onChange={noop} />
		);

		expect(container.querySelector('.btn')).toBeNull();
	});
});
