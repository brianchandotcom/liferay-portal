/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, cleanup, render} from '@testing-library/react';
import {Mock, vi} from 'vitest';

import ListView from '.';
import PageWrapper from '../../test/PageWrapper';

describe('ListView', () => {
	let baseFetch: Mock;

	beforeAll(() => {
		vi.resetAllMocks();
		vi.useFakeTimers();
	});

	beforeEach(() => {
		baseFetch = vi.fn().mockImplementationOnce(() => ({
			items: [{email: 'testray@liferay.com', name: 'Testray User'}],
			lastPage: true,
			pageIndex: 1,
			pageSize: 20,
			totalCount: 100,
		}));

		vi.useFakeTimers();
	});

	afterEach(() => {
		cleanup();
		vi.clearAllTimers();
		vi.restoreAllMocks();
	});

	afterAll(() => {
		vi.useRealTimers();
	});

	it('render ListView with default props', async () => {
		const {asFragment} = render(
			<ListView
				resource="/users"
				tableProps={{
					columns: [{key: 'email', value: 'Email'}],
				}}
			/>,
			{
				wrapper: ({children}) => (
					<PageWrapper fetcher={baseFetch}>{children}</PageWrapper>
				),
			}
		);

		await act(async () => {
			await vi.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('render with items on list and actions', async () => {
		const {container, queryByText} = render(
			<ListView
				resource="/users"
				tableProps={{
					columns: [
						{key: 'name', value: 'Name'},
						{key: 'email', value: 'Email'},
					],
				}}
			/>,
			{
				wrapper: ({children}) => (
					<PageWrapper fetcher={baseFetch}>{children}</PageWrapper>
				),
			}
		);

		await act(async () => {
			await vi.runAllTimers();
		});

		expect(container.querySelectorAll('thead tr')).toHaveLength(1);
		expect(container.querySelectorAll('tbody tr')).toHaveLength(1);
		expect(container.querySelector('table')).toBeTruthy();
		expect(queryByText('testray@liferay.com')).toBeTruthy();
		expect(queryByText('Testray User')).toBeTruthy();
	});

	it('render with empty state', async () => {
		baseFetch = vi.fn().mockImplementation(() => ({
			items: [],
			lastPage: false,
			pageIndex: 1,
			pageSize: 20,
			totalCount: 0,
		}));

		const {container, queryByText} = render(
			<ListView
				resource="/projects"
				tableProps={{
					columns: [
						{key: 'name', value: 'Name'},
						{key: 'email', value: 'Email'},
					],
				}}
			/>,
			{
				wrapper: ({children}) => (
					<PageWrapper fetcher={baseFetch}>{children}</PageWrapper>
				),
			}
		);

		await act(async () => {
			await vi.runAllTimers();
		});

		expect(queryByText('No results found')).toBeTruthy();
		expect(container.querySelector('table')).toBeNull();
	});
});
