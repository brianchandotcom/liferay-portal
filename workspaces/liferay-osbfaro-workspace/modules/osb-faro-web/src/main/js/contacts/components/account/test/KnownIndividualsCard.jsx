/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import KnownIndividualsCard from '../KnownIndividualsCard';

jest.unmock('react-dom');

const dataSourceFn = (total) => () =>
	Promise.resolve(data.mockSearch(data.mockIndividual, total));

const DefaultComponent = (props) => (
	<StaticRouter>
		<KnownIndividualsCard
			channelId="123"
			dataSourceFn={dataSourceFn(3)}
			groupId="23"
			id="23"
			{...props}
		/>
	</StaticRouter>
);

describe('KnownIndividualsCard', () => {
	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders w/ NoResultsDisplay', async () => {
		const {container} = render(
			<DefaultComponent dataSourceFn={dataSourceFn(0)} />
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders w/ ErrorDisplay', async () => {
		const {container, getByText} = render(
			<DefaultComponent dataSourceFn={() => Promise.reject({})} />
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(getByText('An unexpected error occurred.')).toBeTruthy();
	});
});
