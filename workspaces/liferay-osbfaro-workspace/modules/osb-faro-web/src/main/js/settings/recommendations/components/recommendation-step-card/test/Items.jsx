/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import {range} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import Form from '~/shared/components/form';
import * as data from '~/test/data';
import {mockRecommendationPageAssetsReq} from '~/test/graphql-data';
import {waitForLoading} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import Items from '../Items';

jest.unmock('react-dom');

describe('Items', () => {
	it('renders', async () => {
		const {container} = render(
			<MockedProvider
				mocks={[
					mockRecommendationPageAssetsReq(
						range(10).map((i) =>
							data.mockRecommendationPageAsset(i)
						),
						{size: 0}
					),
				]}
			>
				<Provider store={mockStore()}>
					<Form
						initialValues={{
							itemFilters: [
								{
									id: 'includeFilter - url ~ .*custom-assets',
									name: 'includeFilter',
									value: 'url ~ .*custom-assets',
								},
							],
						}}
					>
						{({values: {itemFilters}}) => (
							<Form.Form>
								<Items itemFilters={itemFilters} />
							</Form.Form>
						)}
					</Form>
				</Provider>
			</MockedProvider>
		);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
