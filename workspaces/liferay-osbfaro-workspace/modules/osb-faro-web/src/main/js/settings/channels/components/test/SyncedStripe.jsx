/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import SyncedStripe, {getTitle} from '../SyncedStripe';

jest.unmock('react-dom');

describe('SyncedStripe', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<SyncedStripe channelsSyncedCount={0} sitesSyncedCount={0} />
		);

		expect(container).toMatchSnapshot();
	});

	it.each`
		sitesSyncedCount | channelsSyncedCount
		${1}             | ${0}
		${1}             | ${1}
		${0}             | ${0}
		${0}             | ${1}
		${2}             | ${0}
		${0}             | ${2}
	`(
		'returns correct text if sitesSyncedCount is $sitesSyncedCount and channelsSyncedCount is $channelsSyncedCount',
		({channelsSyncedCount, sitesSyncedCount}) => {
			const {queryByText} = render(
				<SyncedStripe
					channelsSyncedCount={channelsSyncedCount}
					sitesSyncedCount={sitesSyncedCount}
				/>
			);

			if (sitesSyncedCount === 1) {
				if (channelsSyncedCount === 1) {
					expect(
						queryByText(
							`There is ${sitesSyncedCount} site and ${channelsSyncedCount} channel synced to this property.`
						)
					).toBeInTheDocument();
				}
				else {
					expect(
						queryByText(
							`There is ${sitesSyncedCount} site and ${channelsSyncedCount} channels synced to this property.`
						)
					).toBeInTheDocument();
				}
			}
			else {
				if (channelsSyncedCount === 1) {
					expect(
						queryByText(
							`There are ${sitesSyncedCount} sites and ${channelsSyncedCount} channel synced to this property.`
						)
					).toBeInTheDocument();
				}
				else {
					expect(
						queryByText(
							`There are ${sitesSyncedCount} sites and ${channelsSyncedCount} channels synced to this property.`
						)
					).toBeInTheDocument();
				}
			}
		}
	);
});

describe('getTitle', () => {
	it.each`
		sitesSyncedCount | channelsSyncedCount
		${1}             | ${0}
		${1}             | ${1}
		${0}             | ${0}
		${0}             | ${1}
		${2}             | ${0}
		${0}             | ${2}
	`(
		'returns correct text if sitesSyncedCount is $sitesSyncedCount and channelsSyncedCount is $channelsSyncedCount',
		({channelsSyncedCount, sitesSyncedCount}) => {
			const title = getTitle(sitesSyncedCount, channelsSyncedCount);

			if (sitesSyncedCount === 1) {
				if (channelsSyncedCount === 1) {
					expect(title).toEqual(
						`There is 1 site and ${channelsSyncedCount} channel synced to this property.`
					);
				}
				else {
					expect(title).toEqual(
						`There is 1 site and ${channelsSyncedCount} channels synced to this property.`
					);
				}
			}
			else {
				if (channelsSyncedCount === 1) {
					expect(title).toEqual(
						`There are ${sitesSyncedCount} sites and 1 channel synced to this property.`
					);
				}
				else {
					expect(title).toEqual(
						`There are ${sitesSyncedCount} sites and ${channelsSyncedCount} channels synced to this property.`
					);
				}
			}
		}
	);
});
