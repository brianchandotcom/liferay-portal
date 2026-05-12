/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';
import {beforeEach, describe, expect, it, vi} from 'vitest';

import App from './App';
import {Liferay} from './services/liferay';

vi.mock('./services/liferay', () => ({
	Liferay: {
		Util: {
			fetch: vi.fn(),
		},
	},
}));

const mockFetch = vi.mocked(Liferay.Util.fetch);

function mockUser(data: unknown) {
	mockFetch.mockResolvedValue({
		json: vi.fn().mockResolvedValue(data),
	} as unknown as Response);
}

describe('App', () => {
	beforeEach(() => {
		vi.clearAllMocks();
	});

	it('renders the heading', async () => {
		mockUser({});

		render(<App />);

		expect(
			await screen.findByRole('heading', {level: 1})
		).toHaveTextContent('Liferay + Vite + React');
	});

	it('increments the counter on each click', async () => {
		mockUser({});

		render(<App />);

		const button = screen.getByRole('button');

		expect(button).toHaveTextContent('count is 0');

		await userEvent.click(button);
		await userEvent.click(button);

		expect(button).toHaveTextContent('count is 2');
	});

	it('fetches the current user on mount', () => {
		mockUser({});

		render(<App />);

		expect(mockFetch).toHaveBeenCalledWith(
			'/o/headless-admin-user/v1.0/my-user-account'
		);
	});

	it('displays the user data returned by the API', async () => {
		mockUser({emailAddress: 'test@liferay.com', id: 20131});

		render(<App />);

		const pre = await screen.findByText(/"emailAddress"/, {
			selector: 'pre',
		});

		expect(pre).toHaveTextContent('"emailAddress": "test@liferay.com"');
		expect(pre).toHaveTextContent('"id": 20131');
	});
});
