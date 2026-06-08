/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';

import ToggleSwitchModal from '../ToggleSwitchModal';

jest.unmock('react-dom');

describe('ToggleSwitchModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<ToggleSwitchModal
				items={['foo', 'bar', 'baz']}
				message="Select which items you want to toggle"
				onClose={noop}
				onSubmit={jest.fn()}
				title="Toggle some options!"
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
