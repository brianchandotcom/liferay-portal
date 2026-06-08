/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import DefinitionItem from '../DefinitionItem';

jest.unmock('react-dom');

describe('DefinitionItem', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<DefinitionItem />);

		expect(container).toMatchSnapshot();
	});

	it('renders with a label and value', () => {
		const {queryByText} = render(
			<DefinitionItem label="foo" value="bar" />
		);

		expect(queryByText('foo')).toBeTruthy();
		expect(queryByText('bar')).toBeTruthy();
	});

	it('renders with an edit button', () => {
		const {queryByLabelText} = render(<DefinitionItem editable />);

		expect(queryByLabelText('Edit')).toBeTruthy();
	});

	it('renders as an input field with a cancel and submit button', () => {
		const {getByLabelText, queryByLabelText} = render(
			<DefinitionItem editable />
		);

		fireEvent.click(getByLabelText('Edit'));

		jest.runAllTimers();

		expect(queryByLabelText('Cancel')).toBeTruthy();
		expect(queryByLabelText('Submit')).toBeTruthy();
		expect(queryByLabelText('Edit')).toBeNull();
	});
});
