/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import StringMatchInput from '../StringMatchInput';

jest.unmock('react-dom');

describe('StringMatchInput', () => {
	it('renders', () => {
		const {container} = render(
			<StringMatchInput
				metadata="url"
				onStringMatchChange={jest.fn()}
				stringMatch=".*custom-assets"
			/>
		);

		expect(container.querySelector('.base-select-input-root')).toBeNull();
		expect(container).toMatchSnapshot();
	});

	it('renders w/ BaseSelect', () => {
		const {container} = render(
			<StringMatchInput onStringMatchChange={jest.fn()} />
		);

		expect(container.querySelector('.base-select-input-root')).toBeTruthy();
	});
});
