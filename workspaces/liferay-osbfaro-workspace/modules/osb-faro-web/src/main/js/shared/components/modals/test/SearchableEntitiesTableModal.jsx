/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';
import {StaticRouter} from 'react-router';
import {OrderByDirections} from '~/shared/util/constants';
import {EMAIL_ADDRESS, createOrderIOMap} from '~/shared/util/pagination';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import SearchableEntitiesTableModal from '../SearchableEntitiesTableModal';

jest.unmock('react-dom');

const ITEMS = [
	{emailAddress: 'foo@liferay.com', name: 'Foo'},
	{emailAddress: 'bar@liferay.com', name: 'Bar'},
];

const DefaultComponent = (props) => (
	<StaticRouter>
		<SearchableEntitiesTableModal
			columns={[
				{
					accessor: 'name',
					className: 'table-cell-expand',
					label: 'name',
				},
				{
					accessor: 'emailAddress',
					label: 'email',
				},
			]}
			dataSourceFn={() =>
				Promise.resolve({items: ITEMS, total: ITEMS.length})
			}
			groupId="23"
			onClose={noop}
			rowIdentifier="emailAddress"
			{...props}
		/>
	</StaticRouter>
);

describe('SearchableEntitiesTableModal', () => {
	afterEach(cleanup);

	it('renders loading state without ghost table', () => {
		const {container, getByText} = render(
			<DefaultComponent dataSourceFn={() => new Promise(() => {})} />
		);

		expect(getByText('entities')).toBeTruthy();
		expect(container.querySelector('.loading-root')).toBeTruthy();
		expect(container.querySelector('table')).toBeFalsy();
	});

	it('renders w/ defaultParams', async () => {
		const {container} = render(
			<DefaultComponent
				initialOrderIOMap={createOrderIOMap(
					EMAIL_ADDRESS,
					OrderByDirections.Descending
				)}
			/>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		const emailHeaderButton = container.querySelectorAll(
			'.table-head-title > button'
		)[1];

		expect(emailHeaderButton).toHaveTextContent('email');
		expect(
			emailHeaderButton.querySelector(
				'.lexicon-icon-order_arrow_descending'
			)
		).toBeTruthy();
	});
});
