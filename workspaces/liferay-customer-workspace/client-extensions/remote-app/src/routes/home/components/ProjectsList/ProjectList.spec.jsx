/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import {vi} from 'vitest';
import ProjectList from '.';

describe('Project List', () => {
	const koroneikiAccounts = {
		items: [
			{
				name: 'Test Account',
			},
		],
		totalCount: 6,
	};

	window.IntersectionObserver = vi.fn(() => ({
		observer: vi.fn(),
		unobserver: vi.fn(),
	}));

	it('displays "results not found" message if there is no project to show', () => {
		render(<ProjectList />);

		const showNotFoundMessage = screen.getByText(/no results found/i);

		expect(showNotFoundMessage).toBeInTheDocument();
	});

	it('displays projects as cards if has less than 05 projects', () => {
		const {container} = render(
			<ProjectList
				compressed={false}
				koroneikiAccounts={koroneikiAccounts}
			/>
		);

		expect(
			container.getElementsByClassName('cp-project-card-lg').length
		).toBe(1);
	});

	it('displays projects as a list if has more than 05 projects', () => {
		const {container} = render(
			<ProjectList compressed koroneikiAccounts={koroneikiAccounts} />
		);

		expect(container.getElementsByClassName('card-horizontal').length).toBe(
			1
		);
	});
});
