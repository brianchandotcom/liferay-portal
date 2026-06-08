/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import Form from '~/shared/components/form';
import {JobRunDataPeriods} from '~/shared/util/constants';

import Interactions from '../Interactions';

jest.unmock('react-dom');

describe('Interactions', () => {
	it('renders', () => {
		const {container} = render(
			<Form
				initialValues={{
					includePreviousPeriod: false,
					runDataPeriod: JobRunDataPeriods.Last30Days,
				}}
			>
				{() => (
					<Form.Form>
						<Interactions />
					</Form.Form>
				)}
			</Form>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with Include Previous Period Checkbox Checked', () => {
		const {queryByTestId} = render(
			<Form
				initialValues={{
					includePreviousPeriod: true,
					runDataPeriod: JobRunDataPeriods.Last30Days,
				}}
			>
				{() => (
					<Form.Form>
						<Interactions />
					</Form.Form>
				)}
			</Form>
		);

		const checkbox = queryByTestId('include-previous-period-checkbox');

		expect(checkbox.checked).toBeTrue();
	});
});
