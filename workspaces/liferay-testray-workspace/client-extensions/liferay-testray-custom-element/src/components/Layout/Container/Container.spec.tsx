/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import {act} from 'react-dom/test-utils';
import {vi} from 'vitest';

import Container from './Container';

describe('Container', () => {
	beforeAll(() => {
		vi.useFakeTimers();
	});

	it('renders with success', () => {
		const {asFragment, queryByText} = render(
			<Container>
				<div>Element</div>
			</Container>
		);

		expect(queryByText('Element')).toBeTruthy();
		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with title', () => {
		const {container, queryByText} = render(
			<Container title="Testflow">
				<h1>Element</h1>
			</Container>
		);

		expect(container.querySelector('h5')).toHaveTextContent('Testflow');
		expect(queryByText('Element')).toBeTruthy();
	});

	it('renders with title and collapsible actions', async () => {
		const {container} = render(
			<Container collapsable title="Testflow">
				<h1>Element</h1>
			</Container>
		);

		const collapsableButton = container.querySelector('button');

		expect(container.querySelector('h5')).toHaveTextContent('Testflow');
		expect(container.querySelector('.show')).toBeTruthy();
		expect(
			container.querySelector("button[aria-expanded='true']")
		).toBeTruthy();

		await act(async () => {
			await fireEvent.click(collapsableButton as HTMLElement);
		});

		act(() => {
			vi.runAllTimers();
		});

		expect(container.querySelector('.show')).toBeFalsy();
		expect(
			container.querySelector("button[aria-expanded='false']")
		).toBeTruthy();
	});
});
