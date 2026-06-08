/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DataSourceStates, DataSourceStatuses} from '~/shared/util/constants';
import {DataSource} from '~/shared/util/records';

import {getConnectorAvailableDataAlert} from '../getConnectorAvailableDataAlert';

const PREVIOUSLY_SYNCED_MESSAGE =
	'Previously synced data remains available. Reconnect or check your data source connection to resume data syncing.';

const SYNCING_COMPLETES_MESSAGE =
	'Your data may take some time to appear as syncing completes.';

describe('getConnectorAvailableDataAlert', () => {
	describe('ACTIVE', () => {
		const activeDataSource = new DataSource({
			state: DataSourceStates.CredentialsValid,
			status: DataSourceStatuses.Active,
		});

		it('zero data: no alert', () => {
			expect(
				getConnectorAvailableDataAlert(activeDataSource, false)
			).toBeNull();
		});

		it('with data: syncing kind / "Your data may take some time to appear" message', () => {
			expect(
				getConnectorAvailableDataAlert(activeDataSource, true)
			).toEqual({
				displayType: 'info',
				kind: 'syncing',
				message: SYNCING_COMPLETES_MESSAGE,
			});
		});
	});

	describe('INACTIVE (not disconnected)', () => {
		const inactiveDataSource = new DataSource({
			state: DataSourceStates.CredentialsValid,
			status: DataSourceStatuses.Inactive,
		});

		it('without data: no alert', () => {
			expect(
				getConnectorAvailableDataAlert(inactiveDataSource, false)
			).toBeNull();
		});

		it('with data (90d): previously-synced kind / "Previously synced data remains available" message', () => {
			expect(
				getConnectorAvailableDataAlert(inactiveDataSource, true)
			).toEqual({
				displayType: 'info',
				kind: 'previously-synced',
				message: PREVIOUSLY_SYNCED_MESSAGE,
			});
		});
	});

	describe('DISCONNECTED', () => {
		const disconnectedDataSource = new DataSource({
			state: DataSourceStates.Disconnected,
			status: DataSourceStatuses.Inactive,
		});

		it('with data: previously-synced kind / "Previously synced data remains available" message', () => {
			expect(
				getConnectorAvailableDataAlert(disconnectedDataSource, true)
			).toEqual({
				displayType: 'info',
				kind: 'previously-synced',
				message: PREVIOUSLY_SYNCED_MESSAGE,
			});
		});

		it('without data: still shows previously-synced kind / "Previously synced data remains available" message', () => {
			expect(
				getConnectorAvailableDataAlert(disconnectedDataSource, false)
			).toEqual({
				displayType: 'info',
				kind: 'previously-synced',
				message: PREVIOUSLY_SYNCED_MESSAGE,
			});
		});
	});
});
