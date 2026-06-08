/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import SelectFieldInput from '../SelectFieldInput';

jest.unmock('react-dom');

describe('SelectFieldInput', () => {
	it('renders', () => {
		const {getByPlaceholderText} = render(
			<SelectFieldInput groupId="23" />
		);

		expect(
			getByPlaceholderText(Liferay.Language.get('select-field'))
		).toBeInTheDocument();
	});
});
