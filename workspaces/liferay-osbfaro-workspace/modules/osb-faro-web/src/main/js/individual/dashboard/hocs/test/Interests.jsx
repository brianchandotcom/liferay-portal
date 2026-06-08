/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {MemoryRouter, Route} from 'react-router-dom';
import {Routes} from '~/shared/util/router';
import {mockIndividualInterestsReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import Interests from '../Interests';

jest.unmock('react-dom');

describe('Interests', () => {
	it('renders', async () => {
		const {getByText} = render(
			<MockedProvider
				mocks={[
					mockIndividualInterestsReq((defaultVars) => ({
						...defaultVars,
						id: undefined,
						keywords: '',
						size: 2,
					})),
				]}
			>
				<MemoryRouter
					initialEntries={[
						'/workspace/123/456/contacts/individuals/interests',
					]}
				>
					<Route path={Routes.CONTACTS_INDIVIDUALS_INTERESTS}>
						<Interests />
					</Route>
				</MemoryRouter>
			</MockedProvider>
		);

		await waitForLoadingToBeRemoved(document.body);

		jest.runAllTimers();

		expect(getByText('Interest Topics')).toBeInTheDocument();
	});
});
