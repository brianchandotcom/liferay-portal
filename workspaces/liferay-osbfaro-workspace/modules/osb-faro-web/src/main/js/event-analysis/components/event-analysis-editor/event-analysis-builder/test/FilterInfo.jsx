/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import FilterInfo from '../FilterInfo';

jest.unmock('react-dom');

describe('FilterInfo', () => {
	it('render', () => {
		const {container} = render(
			<FilterInfo
				dataType="STRING"
				description="Test description"
				name="Test Name"
				onEditClick={jest.fn()}
				showDescription
			/>
		);

		expect(container.querySelector('.description')).toBeTruthy();
		expect(container.querySelector('.label-root')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('render w/o info', () => {
		const {container} = render(
			<FilterInfo
				dataType="STRING"
				description="Test description"
				name="Test Name"
				onEditClick={jest.fn()}
			/>
		);

		expect(container.querySelector('.description')).toBeNull();
	});

	it('render w/o dataType', () => {
		const {container} = render(
			<FilterInfo
				description="Test description"
				name="Test Name"
				onEditClick={jest.fn()}
				showDescription
			/>
		);

		expect(container.querySelector('.label-root')).toBeNull();
	});
});
