/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {MemoryRouter, Route} from 'react-router-dom';
import {Routes} from '~/shared/util/router';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import Interests, {ContributionsCell} from '../Interests';

jest.unmock('react-dom');

describe('Interests', () => {
	it('renders', async () => {
		const {getByText} = render(
			<MemoryRouter
				initialEntries={[
					'/workspace/23/123/contacts/individuals/known-individuals/321321/interests?delta=2&page=1&field=count&sortOrder=DESC',
				]}
			>
				<Route path={Routes.CONTACTS_INDIVIDUAL_INTERESTS}>
					<Interests channelId="123" groupId="23" id="test" />
				</Route>
			</MemoryRouter>
		);

		await waitForLoadingToBeRemoved(document.body);

		jest.runAllTimers();

		expect(getByText('Contributing Pages')).toBeInTheDocument();
	});
});

describe('ContributionsCell', () => {
	it('renders', () => {
		const {getByText} = render(
			<table>
				<tbody>
					<tr>
						<ContributionsCell data={{relatedPagesCount: 8}} />
					</tr>
				</tbody>
			</table>
		);

		expect(getByText('8 Contributing Pages')).toBeInTheDocument();
	});
});
