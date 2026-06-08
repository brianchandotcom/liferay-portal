/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import AssociatedSegmentsCard from '../AssociatedSegmentsCard';

jest.unmock('react-dom');

const DefaultComponent = (props) => (
	<StaticRouter>
		<AssociatedSegmentsCard
			groupId="23"
			id="123"
			pageUrl="/foo"
			{...props}
		/>
	</StaticRouter>
);

describe('AssociatedSegmentsCard', () => {
	it('renders', async () => {
		const {container} = render(
			<DefaultComponent
				dataSourceFn={() =>
					Promise.resolve(data.mockSearch(data.mockSegment, 2))
				}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders with an error display', async () => {
		const {container, getByText} = render(
			<DefaultComponent dataSourceFn={() => Promise.reject({})} />
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('An unexpected error occurred.')).toBeTruthy();
	});

	it('renders with an no results display', async () => {
		const {container} = render(
			<DefaultComponent
				dataSourceFn={() =>
					Promise.resolve(data.mockSearch(data.mockSegment, 0))
				}
				noResultsRenderer={() => <NoResultsDisplay />}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
