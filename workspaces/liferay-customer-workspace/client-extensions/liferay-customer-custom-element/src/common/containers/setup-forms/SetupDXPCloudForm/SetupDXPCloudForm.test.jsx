/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';

import {act, cleanup, render} from '@testing-library/react';
import gql from 'graphql-tag';
import {MemoryRouter} from 'react-router-dom';
import {vi} from 'vitest';
import SetupDXPCloudForm from './index';

const queryUser = gql`
	query getDXPCloudPageInfo($accountSubscriptionsFilter: String) {
		c {
			accountSubscriptions(filter: $accountSubscriptionsFilter) {
				items {
					accountKey
					externalReferenceCode
					hasDisasterDataCenterRegion
					name
				}
				totalCount
			}

			dXPCDataCenterRegions(sort: "name:asc") {
				items {
					dxpcDataCenterRegionId
					name
					value
				}
				totalCount
			}
		}
	}
`;

const mocksmocks = [
	{
		request: {
			query: queryUser,
			variables: {accountSubscriptionsFilter: `(accountKey eq 'undefined') and (hasDisasterDataCenterRegion eq true or (name eq 'HA DR' or name eq 'Std DR'))`},
		},
		result: {
			data: {
				c: {
					accountSubscriptions: {
						items: [
							{
								accountKey: 1,
								externalReferenceCode: 'CP',
								hasDisasterDataCenterRegion: true,
								name: 'Customer Portal'
							}
						],
						totalCount: 1
					},
					dXPCDataCenterRegions: {
						items: [
							{
								dxpcDataCenterRegionId: 45891,
								name: 'Doha, Qatar',
								value: 'Doha, Qatar',
							},
							{
								dxpcDataCenterRegionId: 45893,
								name: 'Frankfurt, Germany',
								value: 'Frankfurt, Germany',
							},
						],
						totalCount: 2,
					},
				},
			},
		},
	},
];

describe('SetupDXPCloudForm', () => {
	const dXPCDataCenterRegions = [{value: 'Doha, Qatar'}];
	const values = {
		dxp: {
			disasterDataCenterRegion: dXPCDataCenterRegions[0].value,
		},
	};
	let client;

	beforeEach(() => {
		client = {
			query: vi.fn().mockImplementation(() => ({
				data: {
					listTypeDefinitions: {
						items: [
							{
								listTypeEntries: [
									{
										key: '73',
										name: '7.3',
									},
									{
										key: '74',
										name: '7.4',
									},
								],
							},
						],
					},
				},
			})),
		};

		vi.useFakeTimers();
	});

	afterEach(() => {
		cleanup();
		vi.clearAllTimers();
		vi.restoreAllMocks();
	});

	afterAll(() => {
		vi.useRealTimers();
	});

	it('renders', async () => {


		const {asFragment} = render(
			<MemoryRouter>
				<MockedProvider mocks={mocksmocks}>
					<SetupDXPCloudForm
						client={client}
						dxpVersion="7.3"
						handlePage={() => vi.fn()}
						leftButton=""
						listType=""
						project={{
							name: 'Liferay',
						}}
						subscriptionGroupId=""
					/>
				</MockedProvider>
			</MemoryRouter>
		);

		await act(async () => {
			vi.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('disable options based on the value of dxp.disasterDataCenterRegion', async () => {
		const {queryByLabelText} =render(
			<MemoryRouter>
				<MockedProvider mocks={mocksmocks}>
					<SetupDXPCloudForm
						client={client}
						dxpVersion=""
						errors={{}}
						handlePage={() => {}}
						leftButton=""
						listType=""
						project={{name: 'disasterRecovery'}}
						setFieldValue={() => {}}
						setFormAlreadySubmitted={() => {}}
						subscriptionGroupId=""
						touched={{}}
						values={values}
					/>
				</MockedProvider>
			</MemoryRouter>
		);


		await act(async () => {
			vi.runAllTimers();
		});

		const selectPrimaryRegion = queryByLabelText(
			'Primary Data Center Region'
		);

		const selectDisasterRegion = queryByLabelText(
			'Disaster Recovery Data Center Region'
		);
		

		expect(selectPrimaryRegion).toBeInTheDocument();
		expect(selectDisasterRegion).toBeInTheDocument();
		expect(selectPrimaryRegion.value).toBe('doha-qatar');
		expect(selectDisasterRegion.value).toBe('frankfurt-germany');
		expect(selectPrimaryRegion.children[1].disabled).toBe(true);
		expect(selectDisasterRegion.children[0].disabled).toBe(true);
	});
});
