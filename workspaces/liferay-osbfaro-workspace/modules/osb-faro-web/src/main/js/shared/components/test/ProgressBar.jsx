/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import ProgressBar from '../ProgressBar';

jest.unmock('react-dom');

describe('ProgressBar', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<ProgressBar />);

		expect(container).toMatchSnapshot();
	});

	it('renders a complete progress bar without error', () => {
		const {container} = render(<ProgressBar complete error={false} />);

		expect(container).toMatchSnapshot();
	});

	it('renders a progress bar with error', () => {
		const {container} = render(<ProgressBar complete={false} error />);

		expect(container).toMatchSnapshot();
	});
});
