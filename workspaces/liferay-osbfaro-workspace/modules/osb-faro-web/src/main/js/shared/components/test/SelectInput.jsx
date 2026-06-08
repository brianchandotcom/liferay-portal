/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import SelectInput from '../SelectInput';

jest.unmock('react-dom');

describe('SelectInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<SelectInput
				dataSourceFn={() => Promise.resolve([])}
				itemRenderer={jest.fn()}
			/>
		);

		expect(
			container.querySelector('.base-select-container')
		).toBeInTheDocument();
	});

	it('renders with the selected item', () => {
		const {queryByText} = render(
			<SelectInput
				dataSourceFn={() => Promise.resolve([])}
				itemRenderer={({name}) => name}
				selectedItem={{name: 'foo'}}
			/>
		);

		expect(queryByText('foo')).toBeTruthy();
	});
});
