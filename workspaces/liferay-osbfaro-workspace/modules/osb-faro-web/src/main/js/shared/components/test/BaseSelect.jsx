/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {fireEvent, render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import EventAttributeValuesQuery from '~/event-analysis/queries/EventAttributeValuesQuery';
import client from '~/shared/apollo/client';
import {mockEventAttributeValues} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import BaseSelect, {Item} from '../BaseSelect';

jest.unmock('react-dom');

const MOCK_APOLLO_QUERY = {
	mapResultsToProps: (data) => {
		if (data) {
			return {
				data: data.eventAttributeValues.eventAttributeValues,
				total: data.eventAttributeValues.total,
			};
		}

		return {
			data: [],
			total: 0,
		};
	},
	query: EventAttributeValuesQuery,
	variables: {
		channelId: '123',
		eventAttributeDefinitionId: '456',
		eventDefinitionId: '789',
		size: 100,
		start: 0,
	},
};

describe('BaseSelect', () => {
	it('renders', () => {
		const {container} = render(
			<BaseSelect
				dataSourceFn={() => Promise.resolve([])}
				itemRenderer={jest.fn()}
			/>
		);

		expect(
			container.querySelector('.base-select-container')
		).toBeInTheDocument();
	});

	it('renders as disabled', async () => {
		const dataSourceFn = jest.fn();

		const {container} = render(
			<BaseSelect
				dataSourceFn={dataSourceFn}
				disabled
				itemRenderer={({name}) => name}
			/>
		);

		expect(dataSourceFn).not.toHaveBeenCalled();

		fireEvent.click(container.querySelector('.input-group'));

		expect(dataSourceFn).not.toHaveBeenCalled();

		expect(
			container.querySelector('.base-select-container')
		).toBeInTheDocument();
	});

	it('renders w/ selectedItem', async () => {
		const {container} = render(
			<BaseSelect
				dataSourceFn={() =>
					Promise.resolve([
						{name: 'test'},
						{name: 'foo'},
						{name: 'bar'},
					])
				}
				itemRenderer={({name}) => name}
				onFocus={noop}
				selectedItem={{name: 'foo'}}
			/>
		);

		expect(
			container.querySelector('.selected-item-container').innerHTML
		).toEqual('foo');
	});

	it('fetches items with focus', async () => {
		const {container} = render(
			<BaseSelect
				dataSourceFn={() => Promise.resolve([{name: 'test'}])}
				focusOnInit
				itemRenderer={({name}) => name}
				onFocus={noop}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const dropdownMenu = document.body.querySelector('.dropdown-root');

		expect(dropdownMenu).toBeTruthy();
	});

	it('renders w/ menu title', async () => {
		const {getByText} = render(
			<BaseSelect
				dataSourceFn={() => Promise.resolve([{name: 'test'}])}
				focusOnInit
				itemRenderer={({name}) => name}
				menuTitle="Test Menu Title"
				onFocus={noop}
			/>
		);

		expect(getByText('Test Menu Title')).toBeInTheDocument();
	});

	it('focuses on the previous item', async () => {
		const {container, getByText} = render(
			<BaseSelect
				dataSourceFn={() =>
					Promise.resolve([
						{name: 'test'},
						{name: 'foo'},
						{name: 'bar'},
					])
				}
				focusOnInit
				itemRenderer={({name}) => name}
				onFocus={noop}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		fireEvent.keyDown(container.querySelector('.input-root'), {
			key: 'ArrowUp',
			keyCode: 38,
		});

		expect(getByText('bar')).toHaveClass('active');
	});

	it('focuses on the next item', async () => {
		const {container, getByText} = render(
			<BaseSelect
				dataSourceFn={() =>
					Promise.resolve([
						{name: 'test'},
						{name: 'foo'},
						{name: 'bar'},
					])
				}
				focusOnInit
				itemRenderer={({name}) => name}
				onFocus={noop}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		fireEvent.keyDown(container.querySelector('.input-root'), {
			key: 'ArrowDown',
			keyCode: 40,
		});

		expect(getByText('foo')).toHaveClass('active');
	});

	it('renders with Graphql', async () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<MockedProvider mocks={[mockEventAttributeValues()]}>
						<BaseSelect
							graphqlQuery={MOCK_APOLLO_QUERY}
							itemRenderer={jest.fn()}
						/>
					</MockedProvider>
				</Provider>
			</ApolloProvider>
		);

		expect(
			container.querySelector('.base-select-container')
		).toBeInTheDocument();
	});

	it('renders w/ selectedItem with Graphql', async () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<MockedProvider mocks={[mockEventAttributeValues()]}>
						<BaseSelect
							graphqlQuery={MOCK_APOLLO_QUERY}
							itemRenderer={({name}) => name}
							onFocus={noop}
							selectedItem={{name: 'test1'}}
						/>
					</MockedProvider>
				</Provider>
			</ApolloProvider>
		);

		expect(
			container.querySelector('.selected-item-container').innerHTML
		).toEqual('test1');
	});
});

describe('Item', () => {
	it('renders', () => {
		const {getByText} = render(
			<Item item={{name: 'test'}} itemRenderer={({name}) => name} />
		);

		expect(getByText('test')).toBeInTheDocument();
	});

	it('selects an item', () => {
		const onSelect = jest.fn();

		const {getByText} = render(
			<Item
				item={{name: 'test'}}
				itemRenderer={({name}) => name}
				onSelect={onSelect}
			/>
		);

		fireEvent.click(getByText('test'));

		expect(onSelect).toHaveBeenCalledWith({name: 'test'});
	});
});
