/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Property} from '~/shared/util/records';

import DurationInput from '../DurationInput';

jest.unmock('react-dom');

describe('DurationInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<DurationInput
				operatorRenderer={() => <div>operator</div>}
				property={new Property()}
				touched={false}
				valid={false}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with data', () => {
		const {container} = render(
			<DurationInput
				displayValue="Time on Page"
				operatorRenderer={() => <div>operator</div>}
				property={new Property()}
				touched={false}
				valid
				value="123123123"
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders w/ has-error when touched and not valid', () => {
		const {container} = render(
			<DurationInput
				displayValue="Time on Page"
				operatorRenderer={() => <div>operator</div>}
				property={new Property()}
				touched
				valid={false}
				value=""
			/>
		);

		expect(container.querySelector('.has-error')).toBeTruthy();
	});
});
