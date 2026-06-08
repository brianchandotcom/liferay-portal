/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import AddPropertyForm from '../AddPropertyForm';

jest.unmock('react-dom');

describe('DistributionCard AddPropertyForm', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {getByText} = render(<AddPropertyForm />);

		expect(
			getByText(
				Liferay.Language.get('add-a-breakdown-by-individual-attribute')
			)
		).toBeInTheDocument();
		expect(getByText(Liferay.Language.get('save'))).toBeInTheDocument();
	});

	it.skip('renders w/ context dropdown', () => {
		const {container} = render(<AddPropertyForm showContext />);

		expect(container.querySelector('.context-select')).toBeTruthy();
	});
});
