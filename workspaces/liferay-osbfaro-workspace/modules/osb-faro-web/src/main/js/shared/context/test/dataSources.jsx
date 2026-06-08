/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	cleanup,
	render,
	waitForElementToBeRemoved,
} from '@testing-library/react';
import React from 'react';
import * as API from '~/shared/api';
import * as data from '~/test/data';

import {DataSourcesProvider, useDataSources} from '../dataSources';

jest.unmock('react-dom');

const ChildComponent = () => {
	const {empty, error, items, loading} = useDataSources();

	return (
		<>
			{empty && <p>empty</p>}

			{error && <p>error</p>}

			{!!items.length && (
				<div id="items">
					{items.map((_, index) => (
						<p key={index}>item</p>
					))}
				</div>
			)}

			{loading && <p>loading</p>}
		</>
	);
};

const renderWithProvider = (groupId = '1') =>
	render(
		<DataSourcesProvider groupId={groupId}>
			<ChildComponent />
		</DataSourcesProvider>
	);

describe('DataSourcesProvider', () => {
	afterEach(cleanup);

	it('renders empty', async () => {
		API.dataSource.search.mockReturnValueOnce(
			Promise.resolve(data.mockSearch([], 0))
		);

		const {getByText} = renderWithProvider();

		await waitForElementToBeRemoved(() => getByText('loading'));

		expect(getByText('empty')).toBeInTheDocument();
	});

	it('renders error', async () => {
		API.dataSource.search.mockReturnValueOnce(
			Promise.reject({IS_CANCELLATION_ERROR: ''})
		);

		const {getByText} = renderWithProvider();

		await waitForElementToBeRemoved(() => getByText('loading'));

		expect(getByText('error')).toBeInTheDocument();
	});

	it('renders loading', () => {
		API.dataSource.search.mockReturnValueOnce(
			Promise.resolve(data.mockSearch(data.mockLiferayDataSource, 1))
		);

		const {getByText} = renderWithProvider();

		expect(getByText('loading')).toBeInTheDocument();
	});

	it('renders success', async () => {
		API.dataSource.search.mockReturnValueOnce(
			Promise.resolve(data.mockSearch(data.mockLiferayDataSource, 1))
		);

		const {container, getByText} = renderWithProvider();

		await waitForElementToBeRemoved(() => getByText('loading'));

		const itemsSelector = container.querySelector('#items');

		expect(itemsSelector.children).toHaveLength(1);
		expect(getByText('item')).toBeInTheDocument();
	});

	it('skips the request when groupId is missing', () => {
		API.dataSource.search.mockClear();

		renderWithProvider('0');

		expect(API.dataSource.search).not.toHaveBeenCalled();
	});

	it('skips the request when skip is true', () => {
		API.dataSource.search.mockClear();

		render(
			<DataSourcesProvider groupId="1" skip>
				<ChildComponent />
			</DataSourcesProvider>
		);

		expect(API.dataSource.search).not.toHaveBeenCalled();
	});
});

describe('useDataSources', () => {
	afterEach(cleanup);

	it('throws when used outside of a DataSourcesProvider', () => {
		const consoleError = jest
			.spyOn(console, 'error')
			.mockImplementation(() => {});

		expect(() => render(<ChildComponent />)).toThrow(
			'useDataSources must be used within a DataSourcesProvider'
		);

		consoleError.mockRestore();
	});
});
