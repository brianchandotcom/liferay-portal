/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';

import LoadingModal from '../LoadingModal';

jest.unmock('react-dom');

describe('LoadingModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<LoadingModal onClose={noop} />);
		expect(container).toMatchSnapshot();
	});

	it('renders with a message and a title', () => {
		const {queryByText} = render(
			<LoadingModal message="foo" onClose={noop} title="bar" />
		);

		expect(queryByText('foo')).toBeTruthy();
		expect(queryByText('bar')).toBeTruthy();
	});

	it('renders an icon', () => {
		const {container} = render(<LoadingModal icon="foo" onClose={noop} />);

		expect(container.querySelector('.icon-root')).toBeTruthy();
	});
});
