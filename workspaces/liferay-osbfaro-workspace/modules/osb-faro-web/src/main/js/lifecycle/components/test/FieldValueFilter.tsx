/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {LifecycleContextProvider} from '../../context/LifecycleContext';
import FieldValueFilter from '../FieldValueFilter';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({channelId: '456', groupId: '2000'}),
}));

const renderFilter = (props = {}) =>
	render(
		<LifecycleContextProvider lifecycleId="1">
			<FieldValueFilter
				entityLabel="Industries"
				fieldMappingFieldName="industry"
				filterKey="industryFilter"
				{...props}
			/>
		</LifecycleContextProvider>
	);

describe('FieldValueFilter', () => {
	afterEach(cleanup);

	it('renders the loading state while the request is pending', () => {
		const useRequest = require('~/shared/hooks/useRequest');
		useRequest.useRequest = jest.fn(() => ({loading: true}));

		const {container} = renderFilter();

		expect(container.querySelector('.loading-root')).toBeInTheDocument();
	});

	it('renders the "all-x" label when the filter is empty', () => {
		const useRequest = require('~/shared/hooks/useRequest');
		useRequest.useRequest = jest.fn(() => ({
			data: {items: ['Tech', 'Finance']},
			loading: false,
		}));

		const {getByText} = renderFilter();

		expect(getByText('All Industries')).toBeInTheDocument();
	});

	it('passes the fieldMappingFieldName through to the request', () => {
		const useRequest = require('~/shared/hooks/useRequest');
		const spy = jest.fn(() => ({data: {items: []}, loading: false}));
		useRequest.useRequest = spy;

		renderFilter({
			entityLabel: 'Countries',
			fieldMappingFieldName: 'country',
			filterKey: 'countryFilter',
		});

		expect(spy).toHaveBeenCalledWith(
			expect.objectContaining({
				variables: expect.objectContaining({
					channelId: '456',
					fieldMappingFieldName: 'country',
					groupId: '2000',
				}),
			})
		);
	});
});
