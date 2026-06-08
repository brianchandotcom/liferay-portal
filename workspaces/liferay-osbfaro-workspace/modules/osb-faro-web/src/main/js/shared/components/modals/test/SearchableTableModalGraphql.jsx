/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render, screen, waitFor} from '@testing-library/react';
import {noop, range} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {
	getMapResultToProps,
	mapPropsToOptions,
} from '~/segment/segment-editor/dynamic/mappers/dxp-entity-bag-mapper';
import OrganizationsQuery from '~/segment/segment-editor/dynamic/queries/OrganizationsQuery';
import {createOrderIOMap} from '~/shared/util/pagination';
import {Routes} from '~/shared/util/router';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import SearchableTableModalGraphql from '../SearchableTableModalGraphql';

jest.unmock('react-dom');

const COLUMNS = [
	{
		accessor: 'name',
		label: 'name',
	},
];

const mockItems = range(5).map((i) => ({
	__typename: 'Organization',
	id: String(i),
	name: `fooOrganization-${i}`,
}));

const defaultProps = {
	columns: COLUMNS,
	graphqlQuery: OrganizationsQuery,
	groupId: '23',
	initialDelta: 5,
	initialOrderIOMap: createOrderIOMap('name'),
	mapPropsToOptions,
	mapResultToProps: getMapResultToProps('organizations'),
	onClose: noop,
	onSubmit: noop,
};

const DefaultComponent = ({
	mocks = [
		{
			request: {
				query: OrganizationsQuery,
				variables: {
					keywords: '',
					size: 5,
					sort: {column: 'name', type: 'ASC'},
					start: 0,
				},
			},
			result: {
				data: {
					organizations: {
						__typename: 'OrganizationBag',
						dxpEntities: mockItems,
						total: 5,
					},
				},
			},
		},
	],
	...props
}) => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/workspace/23/settings/data-source']}>
			<Route path={Routes.SETTINGS_DATA_SOURCE_LIST}>
				<MockedProvider addTypename={false} mocks={mocks}>
					<SearchableTableModalGraphql {...defaultProps} {...props} />
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('SearchableTableModalGraphql', () => {
	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders with a custom title', async () => {
		const {container} = render(<DefaultComponent title="Custom Title" />);

		await waitForLoadingToBeRemoved(container);

		expect(container.querySelector('.modal-title')).toHaveTextContent(
			'Custom Title'
		);
	});

	it('renders with a custom submit button message', async () => {
		render(<DefaultComponent submitMessage="Custom Submit Message" />);

		await waitForLoadingToBeRemoved();

		await waitFor(() => {
			expect(
				screen.getByText('Custom Submit Message')
			).toBeInTheDocument();
		});
	});

	it('renders with preselected items', async () => {
		const {container} = render(
			<DefaultComponent
				selectedItems={[{id: '0', name: 'fooOrganization-0'}]}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(
			container.querySelector('input[type="checkbox"]:checked')
		).toBeTruthy();
	});
});
