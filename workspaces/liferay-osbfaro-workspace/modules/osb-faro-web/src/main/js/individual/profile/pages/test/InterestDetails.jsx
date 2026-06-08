/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';
import {Individual} from '~/shared/util/records';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import InterestDetails from '../InterestDetails';

const defaultProps = {
	active: 'true',
	groupId: '23',
	id: 'test',
	individual: new Individual(data.mockIndividual()),
	interestId: 1,
};

jest.unmock('react-dom');

describe('InterestDetails', () => {
	it('renders', async () => {
		const {container, getByText} = render(
			<StaticRouter>
				<InterestDetails {...defaultProps} />
			</StaticRouter>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Back to Interests')).toBeInTheDocument();
	});

	it('renders an active pages list tab', async () => {
		const {container, getByText} = render(
			<StaticRouter>
				<InterestDetails {...defaultProps} />
			</StaticRouter>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Active Pages')).toBeTruthy();
		expect(getByText('Active Pages').parentElement).toHaveClass('active');
	});

	it('renders an inactive pages list tab', async () => {
		const {container, getByText} = render(
			<StaticRouter>
				<InterestDetails {...defaultProps} active="false" />
			</StaticRouter>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Inactive Pages')).toBeTruthy();
		expect(getByText('Inactive Pages').parentElement).toHaveClass('active');
	});
});
