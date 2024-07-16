/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render, screen} from '@testing-library/react';
import {vi} from 'vitest';
import SearchBar from '.';

describe('Home: SearchBar', () => {
	const functionMock = vi.fn();

	it('updates input while typing value', () => {
		const searchProjectName = 'Test Account 01';

		render(<SearchBar onSearchSubmit={functionMock} />);

		const searchInput = screen.getByPlaceholderText(/find a project/i);

		fireEvent.change(searchInput, {target: {value: searchProjectName}});
		expect(searchInput.value).toBe('Test Account 01');
		expect(functionMock).toHaveBeenCalled();
	});
});
