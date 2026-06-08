/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';

import AddDataSource from '../AddDataSource';

jest.unmock('react-dom');

describe('DistributionCard AddDataSource', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {getByText} = render(
			<BrowserRouter>
				<AddDataSource groupId="123" />
			</BrowserRouter>
		);

		expect(
			getByText(Liferay.Language.get('add-data-source'))
		).toBeInTheDocument();
		expect(
			getByText(Liferay.Language.get('distribution'))
		).toBeInTheDocument();
	});
});
