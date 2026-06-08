/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import * as API from '~/shared/api';
import {mockFieldMapping, mockMapping} from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import {DataTransformation, processFieldMappings} from '../DataTransformation';

jest.unmock('react-dom');

const defaultProps = {
	groupId: '23',
	id: '123',
	onSubmit: jest.fn(),
};

const WrappedComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={['/workspace/23/settings/data-source/123']}
		>
			<Route path="/workspace/:groupId/settings/data-source/:id">
				<MockedProvider
					cache={
						new InMemoryCache({
							addTypename: false,
							freezeResults: false,
						})
					}
				>
					<DataTransformation {...defaultProps} {...props} />
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('processFieldMappings', () => {
	it('returns fieldMappings', () => {
		const foo = 'foo';
		const bar = 'bar';
		const baz = 'baz';

		const inputValue = fromJS([
			{source: {name: foo}, suggestion: {}},
			{source: {name: bar}, suggestion: {}},
			{source: {name: baz}, suggestion: {}},
		]);

		const result = processFieldMappings(inputValue);

		expect(result.length).toEqual(3);

		expect(result).toMatchSnapshot();
	});
});

describe('DataTransformation', () => {
	it('renders', async () => {
		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders w/ the done button enabled', async () => {
		API.dataSource.fetchMappings.mockReturnValue(
			Promise.resolve([
				mockMapping('Matched Field', {
					suggestions: [mockFieldMapping()],
				}),
			])
		);

		const {getByText} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(document.body);

		expect(getByText('Done')).not.toBeDisabled();
	});

	it('renders w/ the done button disabled if there are duplicate CSV field mappings', async () => {
		API.dataSource.fetchMappings.mockReturnValue(
			Promise.resolve([
				mockMapping('Has default match 1', {
					suggestions: [
						mockFieldMapping(null, {name: 'foo'}),
						mockFieldMapping(null, {name: 'jack', value: 'dupe'}),
					],

					// eslint-disable-next-line comma-dangle
				}),
				mockMapping('Has default match 2', {
					suggestions: [
						mockFieldMapping(null, {name: 'bar'}),
						mockFieldMapping(null, {name: 'jack', value: 'dupe'}),
					],

					// eslint-disable-next-line comma-dangle
				}),
				mockMapping('No default match'),
			])
		);

		const {getByText} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(document.body);

		expect(getByText('Done')).toBeDisabled();
	});
});
