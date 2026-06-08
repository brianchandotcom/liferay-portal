/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import Modal from '../index';

jest.unmock('react-dom');

describe('Modal', () => {
	it('renders', () => {
		const {container} = render(<Modal />);

		expect(container).toMatchSnapshot();
	});

	it('renders as a large warning modal', () => {
		const {container} = render(<Modal size="lg" type="warning" />);

		expect(container.querySelector('.modal-lg')).toBeTruthy();
		expect(container.querySelector('.modal-warning')).toBeTruthy();
	});

	it('renders with children', () => {
		const {queryByText} = render(
			<Modal>
				<span>Modal child</span>
			</Modal>
		);

		expect(queryByText('Modal child')).toBeTruthy();
	});
});
