/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {MemoryRouter, Route} from 'react-router-dom';
import {AttributeTypes} from '~/event-analysis/utils/types';
import {Routes} from '~/shared/util/router';
import * as data from '~/test/data';
import {mockEventAttributeDefinitionsReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import GlobalAttributeList from '../GlobalAttributeList';

jest.unmock('react-dom');

const mockGroupId = '23';

const Wrapper = ({children, mocks = []}) => (
	<MemoryRouter
		initialEntries={[
			`/workspace/${mockGroupId}/settings/definitions/event-attributes/global?delta=1`,
		]}
	>
		<Route path={Routes.SETTINGS_DEFINITIONS_EVENT_ATTRIBUTES_GLOBAL}>
			<MockedProvider addTypename={false} mocks={mocks}>
				{children}
			</MockedProvider>
		</Route>
	</MemoryRouter>
);

describe('GlobalAttributeList', () => {
	it('renders', async () => {
		const mocks = [
			mockEventAttributeDefinitionsReq(
				[
					data.mockEventAttributeDefinition(0, {
						__typename: 'EventAttributeDefinition',
					}),
				],
				{
					keyword: '',
					page: 0,
					size: 1,
					sort: {column: 'name', type: 'ASC'},
					type: AttributeTypes.Global,
				}
			),
		];

		const {container} = render(
			<Wrapper mocks={mocks}>
				<GlobalAttributeList />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
