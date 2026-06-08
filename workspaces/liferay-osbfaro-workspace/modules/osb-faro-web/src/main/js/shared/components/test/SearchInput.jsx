/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, createEvent, fireEvent, render} from '@testing-library/react';
import React from 'react';

import SearchInput from '../SearchInput';

jest.unmock('react-dom');

describe('SearchInput', () => {
	afterEach(cleanup);

	it('renders input', () => {
		const {container} = render(<SearchInput />);
		expect(container).toMatchSnapshot();
	});

	it('returns input value if key is `Enter`', () => {
		const value = 'foo';
		const onSubmit = jest.fn();

		const {getByPlaceholderText} = render(
			<SearchInput onSubmit={onSubmit} value={value} />
		);

		const node = getByPlaceholderText('Search');
		const event = createEvent.keyDown(node, {key: 'Enter'});
		fireEvent(node, event);
		expect(onSubmit).toHaveBeenCalledWith(value);
	});

	it('does not call onSubmit or event.preventDefault', () => {
		const onSubmit = jest.fn();

		const {getByPlaceholderText} = render(
			<SearchInput onSubmit={onSubmit} />
		);
		const node = getByPlaceholderText('Search');
		const event = createEvent.keyDown(node, {key: 'a'});
		fireEvent(node, event);

		expect(onSubmit).not.toHaveBeenCalled();
	});

	it('returns input value', () => {
		const value = 'foo';
		const onChange = jest.fn();

		const {getByPlaceholderText} = render(
			<SearchInput onChange={onChange} />
		);

		fireEvent.change(getByPlaceholderText('Search'), {target: {value}});

		expect(onChange).toHaveBeenCalledWith(value);
	});

	it('renders a button to clear input', () => {
		const {container} = render(<SearchInput value="foo" />);
		expect(container.querySelector('.lexicon-icon-times')).toBeTruthy();
	});
});
