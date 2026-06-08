/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import PropertyCell from '../Property';

jest.unmock('react-dom');

describe('PropertyCell', () => {
	it('renders', () => {
		const {container} = render(
			<PropertyCell
				data={{
					name: 'email',
					value: 'TestTest@liferay.com',
				}}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders - when has no value', () => {
		const {getByText} = render(
			<PropertyCell
				data={{
					name: 'email',
					value: '',
				}}
			/>
		);

		expect(getByText('-')).toBeTruthy();
	});
});
