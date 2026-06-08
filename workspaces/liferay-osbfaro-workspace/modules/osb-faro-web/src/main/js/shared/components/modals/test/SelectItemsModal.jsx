/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, cleanup, fireEvent, render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';
import {MemoryRouter, Route} from 'react-router-dom';
import {createOrderIOMap} from '~/shared/util/pagination';
import {Routes} from '~/shared/util/router';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import SelectItemsModal, {ItemComponent} from '../SelectItemsModal';

jest.unmock('react-dom');

const ITEM_COMPONENT_MOCK = {
	name: 'test',
};

const MESSAGE = 'message';

const SELECTED_ITEMS_MOCK = [
	{id: 1, name: 'test1'},
	{id: 2, name: 'test2'},
];

const DefaultComponent = (props) => (
	<MemoryRouter initialEntries={['/workspace/23/settings/data-source']}>
		<Route path={Routes.SETTINGS_DATA_SOURCE_LIST}>
			<SelectItemsModal
				dataSourceFn={() => Promise.resolve()}
				groupId="23"
				initialOrderIOMap={createOrderIOMap('name')}
				{...props}
			/>
		</Route>
	</MemoryRouter>
);

describe('SelectItemsModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('renders with a custom title', () => {
		const {container} = render(<DefaultComponent title="Custom Title" />);

		expect(container).toMatchSnapshot();
	});

	it('renders with a custom submit message', () => {
		const {container} = render(
			<DefaultComponent submitMessage="Custom Submit Message" />
		);

		expect(container).toMatchSnapshot();
	});

	it('renders items as an entity list', () => {
		const {container} = render(<DefaultComponent entityType={4} />);

		expect(container).toMatchSnapshot();
	});

	it('renders without the sort button', () => {
		const {container} = render(<DefaultComponent showSortButton={false} />);

		expect(container).toMatchSnapshot();
	});

	it('renders without a toolbar', () => {
		const {container} = render(<DefaultComponent showToolbar={false} />);

		expect(container).toMatchSnapshot();
	});

	it('renders with selectedItems', () => {
		const {container} = render(
			<DefaultComponent selectedItems={SELECTED_ITEMS_MOCK} />
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with selectedItems with the disable selected datasource', async () => {
		const {container} = render(
			<DefaultComponent
				disabledSelectedDataSourceFn={() =>
					Promise.resolve({items: SELECTED_ITEMS_MOCK})
				}
			/>
		);

		jest.runAllTimers();
		await waitForLoadingToBeRemoved(container);
		expect(container).toMatchSnapshot();
	});

	it('renders with a successful submit', async () => {
		const {container, getByText} = render(
			<DefaultComponent
				onClose={noop}
				onSubmit={() => Promise.resolve()}
				requireSelection={false}
				submitMessage={MESSAGE}
			/>
		);

		fireEvent.click(getByText(MESSAGE));

		jest.runAllTimers();
		await waitForLoadingToBeRemoved(container);
		expect(container).toMatchSnapshot();
	});

	it('renders with an unsuccessful submit', async () => {
		const {container, getByText} = render(
			<DefaultComponent
				onClose={noop}
				onSubmit={() =>
					Promise.reject({IS_CANCELLATION_ERROR: 'error'})
				}
				requireSelection={false}
				submitMessage={MESSAGE}
			/>
		);

		fireEvent.click(getByText(MESSAGE));

		// IS_CANCELLATION_ERROR prevents submitting from resetting to false,
		// so the loading spinner stays. Flush pending timers/promises only.

		await act(async () => {
			jest.runOnlyPendingTimers();
		});
		expect(container).toMatchSnapshot();
	});

	it('renders with a blank submit', async () => {
		const {container, getByText} = render(
			<DefaultComponent
				onClose={noop}
				onSubmit={noop({IS_CANCELLATION_ERROR: 'error'})}
				requireSelection={false}
				submitMessage={MESSAGE}
			/>
		);

		fireEvent.click(getByText(MESSAGE));

		// Non-Promise onSubmit path calls onClose but does not reset submitting,
		// so the loading spinner stays. Flush pending timers/promises only.

		await act(async () => {
			jest.runOnlyPendingTimers();
		});
		expect(container).toMatchSnapshot();
	});

	describe('ItemComponent', () => {
		afterEach(cleanup);

		it('renders', () => {
			const {container} = render(
				<ItemComponent item={ITEM_COMPONENT_MOCK} />
			);

			expect(container).toMatchSnapshot();
		});

		it('renders with a custom className', () => {
			const {container} = render(
				<ItemComponent className="test" item={ITEM_COMPONENT_MOCK} />
			);

			expect(container).toMatchSnapshot();
		});
	});
});
