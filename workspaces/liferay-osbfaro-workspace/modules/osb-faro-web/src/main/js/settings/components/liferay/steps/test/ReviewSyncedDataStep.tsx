/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider, MockedResponse} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {MemoryRouter} from 'react-router-dom';
import DataSourceQuery from '~/shared/queries/DataSourceQuery';
import {DataSourceTypes, OrderByDirections} from '~/shared/util/constants';

import {ReviewSyncedDataStep} from '../ReviewSyncedDataStep';

jest.unmock('react-dom');

const defaultMock = {
	request: {
		query: DataSourceQuery,
		variables: {
			size: 1,
			sort: {column: 'createDate', type: OrderByDirections.Descending},
			type: DataSourceTypes.Liferay,
		},
	},
	result: {
		data: {
			dataSources: [
				{
					contactsSyncDetails: {selected: true},
					id: '123',
					sitesSyncDetails: {selected: false},
				},
			],
		},
	},
};

const Wrapper = ({
	children,
	mocks = [defaultMock],
}: {
	children: React.ReactNode;
	mocks?: MockedResponse[];
}) => (
	<MemoryRouter>
		<MockedProvider addTypename={false} mocks={mocks}>
			{children}
		</MockedProvider>
	</MemoryRouter>
);

describe('ReviewSyncedDataStep', () => {
	it('renders', () => {
		const {container} = render(
			<Wrapper>
				<ReviewSyncedDataStep onNext={jest.fn()} onPrev={jest.fn()} />
			</Wrapper>
		);

		expect(container).toMatchSnapshot();
	});
});
