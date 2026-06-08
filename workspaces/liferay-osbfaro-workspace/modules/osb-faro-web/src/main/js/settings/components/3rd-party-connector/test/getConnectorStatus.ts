/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DataSourceStates, DataSourceStatuses} from '~/shared/util/constants';
import {DataSource} from '~/shared/util/records';

import {getConnectorStatus} from '../getConnectorStatus';
import {ConnectorStatus} from '../types';

describe('getConnectorStatus', () => {
	describe('ACTIVE', () => {
		it('returns Active when connected with zero accounts', () => {
			const dataSource = new DataSource({
				state: DataSourceStates.CredentialsValid,
				status: DataSourceStatuses.Active,
			});

			expect(getConnectorStatus(dataSource)).toBe(ConnectorStatus.Active);
		});

		it('returns Active when connected with accounts', () => {
			const dataSource = new DataSource({
				state: DataSourceStates.CredentialsValid,
				status: DataSourceStatuses.Active,
			});

			expect(getConnectorStatus(dataSource)).toBe(ConnectorStatus.Active);
		});
	});

	describe('INACTIVE', () => {
		it('returns Inactive when token was generated but not validated and no data was received', () => {
			const dataSource = new DataSource({
				state: DataSourceStates.Unconfigured,
				status: DataSourceStatuses.Inactive,
			});

			expect(getConnectorStatus(dataSource)).toBe(
				ConnectorStatus.Inactive
			);
		});

		it('returns Inactive when previously connected with data but stopped receiving for 90 days', () => {
			const dataSource = new DataSource({
				state: DataSourceStates.CredentialsValid,
				status: DataSourceStatuses.Inactive,
			});

			expect(getConnectorStatus(dataSource)).toBe(
				ConnectorStatus.Inactive
			);
		});
	});

	describe('DISCONNECTED', () => {
		it('returns Disconnected when the user manually disconnected the data source', () => {
			const dataSource = new DataSource({
				state: DataSourceStates.Disconnected,
				status: DataSourceStatuses.Inactive,
			});

			expect(getConnectorStatus(dataSource)).toBe(
				ConnectorStatus.Disconnected
			);
		});
	});

	it('falls back to Inactive when dataSource is undefined', () => {
		expect(getConnectorStatus(undefined)).toBe(ConnectorStatus.Inactive);
	});
});
