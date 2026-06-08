/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import client from '~/shared/apollo/client';
import {Segment} from '~/shared/util/records';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import Overview from '../Overview';

jest.unmock('react-dom');

const segment = new Segment(
	data.mockSegment(0, {
		criteriaString: "(demographics/middleName/value eq 'additionalName')",
		referencedObjects: {
			fieldMappings: {
				individual: {
					demographics: {
						middleName: {
							context: 'demographics',
							dataSourceFieldNames: {},
							displayName: 'Middle Name',
							displayType: 'input-field',
							id: 'middleName',
							name: 'additionalName',
							ownerType: 'individual',
							rawType: 'Text',
							type: 'text',
						},
					},
				},
			},
		},
	})
);

jest.mock('~/shared/hooks/useTimeZone', () => ({
	useTimeZone: () => ({
		timeZoneId: 'UTC',
	}),
}));

describe('SegmentOverview', () => {
	it('renders', async () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<ApolloProvider client={client}>
					<StaticRouter>
						<Overview groupId="23" segment={segment} />
					</StaticRouter>
				</ApolloProvider>
			</Provider>
		);

		await waitForLoadingToBeRemoved(container).then(() => {
			expect(container).toMatchSnapshot();
		});
	});
});
