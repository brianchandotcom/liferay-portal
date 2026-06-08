/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import {AlertTypes} from '../Alert';
import EmbeddedAlertList from '../EmbeddedAlertList';

jest.unmock('react-dom');

describe('EmbeddedAlertList', () => {
	it('renders', () => {
		const {container} = render(
			<EmbeddedAlertList
				alerts={[
					{
						iconSymbol: 'exclamation-full',
						message: 'foo bar',
						title: 'Test Title',
						type: AlertTypes.Danger,
					},
				]}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
