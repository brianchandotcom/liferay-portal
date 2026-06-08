/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Header from '../Header';

jest.unmock('react-dom');

describe('Modal Header', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<Header title="Foo" />);

		expect(container).toMatchSnapshot();
	});

	it('renders a modal header with an icon', () => {
		const {container} = render(
			<Header iconSymbol="warning-full" title="Foo" />
		);

		expect(container.querySelector('.icon-root')).not.toBeNull();
	});

	it('renders a modal header w/ a border', () => {
		const {container} = render(<Header border title="Foo" />);

		expect(container.querySelector('.border')).not.toBeNull();
	});
});
