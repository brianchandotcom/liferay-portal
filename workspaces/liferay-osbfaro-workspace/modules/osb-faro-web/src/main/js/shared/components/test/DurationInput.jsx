/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import DurationInput from '../DurationInput';

jest.unmock('react-dom');

describe('DurationInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<DurationInput />);

		expect(container).toMatchSnapshot();
	});

	it('renders default unit as seconds', () => {
		const {queryByDisplayValue} = render(<DurationInput value={1000} />);

		expect(queryByDisplayValue('00:00:01')).toBeTruthy();
	});

	it('renders default unit as minutes', () => {
		const {queryByDisplayValue} = render(<DurationInput value={60000} />);

		expect(queryByDisplayValue('00:01:00')).toBeTruthy();
	});

	it('renders default unit as hours', () => {
		const {queryByDisplayValue} = render(<DurationInput value={3600000} />);

		expect(queryByDisplayValue('01:00:00')).toBeTruthy();
	});
});
