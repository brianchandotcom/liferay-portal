/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import ClearAllButton from '../ClearAllButton';

jest.unmock('react-dom');

describe('ClearAllButton', () => {
	afterEach(cleanup);

	it('renders the clear-all button', () => {
		render(<ClearAllButton onClear={() => {}} />);

		expect(
			screen.getByRole('button', {name: /clear all/i})
		).toBeInTheDocument();
	});

	it('does not show the confirmation modal until the button is clicked', () => {
		render(<ClearAllButton onClear={() => {}} />);

		expect(screen.queryByRole('dialog')).not.toBeInTheDocument();
	});

	it('opens the confirmation modal on click', () => {
		render(<ClearAllButton onClear={() => {}} />);

		fireEvent.click(screen.getByRole('button', {name: /clear all/i}));

		expect(screen.getByRole('dialog')).toBeInTheDocument();
	});

	it('does not invoke onClear when only the trigger is clicked', () => {
		const onClear = jest.fn();

		render(<ClearAllButton onClear={onClear} />);

		fireEvent.click(screen.getByRole('button', {name: /clear all/i}));

		expect(onClear).not.toHaveBeenCalled();
	});
});
