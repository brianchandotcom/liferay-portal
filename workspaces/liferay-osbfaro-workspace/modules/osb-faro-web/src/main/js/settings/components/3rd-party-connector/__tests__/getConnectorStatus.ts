import {ConnectorStatus} from '../types';
import {DataSource} from 'shared/util/records';
import {DataSourceStates, DataSourceStatuses} from 'shared/util/constants';
import {getConnectorStatus} from '../getConnectorStatus';

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
