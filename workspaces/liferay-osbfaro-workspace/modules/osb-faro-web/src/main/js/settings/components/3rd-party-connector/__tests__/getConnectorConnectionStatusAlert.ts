import {DataSource} from 'shared/util/records';
import {DataSourceStates, DataSourceStatuses} from 'shared/util/constants';
import {getConnectorConnectionStatusAlert} from '../getConnectorConnectionStatusAlert';

describe('getConnectorConnectionStatusAlert', () => {
	describe('ACTIVE', () => {
		const activeDataSource = new DataSource({
			state: DataSourceStates.CredentialsValid,
			status: DataSourceStatuses.Active
		});

		it('with zero accounts: warning / "successfully connected" message', () => {
			expect(
				getConnectorConnectionStatusAlert(activeDataSource, 0)
			).toEqual({
				displayType: 'warning',
				message:
					'You have successfully connected to your data source. Complete your data source configuration to start syncing data.'
			});
		});

		it('with accounts: success / "all data is up to date" message', () => {
			expect(
				getConnectorConnectionStatusAlert(activeDataSource, 5)
			).toEqual({
				displayType: 'success',
				message:
					'All data coming from this data source is up to date. There are no errors to report.'
			});
		});
	});

	describe('INACTIVE (not disconnected)', () => {
		it('no connection without data: warning / "your token was generated" message', () => {
			const dataSource = new DataSource({
				state: DataSourceStates.Unconfigured,
				status: DataSourceStatuses.Inactive
			});

			expect(getConnectorConnectionStatusAlert(dataSource, 0)).toEqual({
				displayType: 'warning',
				message:
					'Your token was generated successfully. Complete your data source configuration to start syncing data.'
			});
		});

		it('was connected with data, no data for 90 days: warning / "data is no longer being received - review" message', () => {
			const dataSource = new DataSource({
				state: DataSourceStates.CredentialsValid,
				status: DataSourceStatuses.Inactive
			});

			expect(getConnectorConnectionStatusAlert(dataSource, 12)).toEqual({
				displayType: 'warning',
				message:
					'Data is no longer being received. Review your data source configuration to confirm it is still active.'
			});
		});
	});

	describe('DISCONNECTED', () => {
		it('manual disconnection: warning / "data is no longer being received - reconnect" message', () => {
			const dataSource = new DataSource({
				state: DataSourceStates.Disconnected,
				status: DataSourceStatuses.Inactive
			});

			expect(getConnectorConnectionStatusAlert(dataSource, 7)).toEqual({
				displayType: 'warning',
				message:
					'Data is no longer being received. Reconnect to resume syncing.'
			});
		});
	});

	it('falls back to the INACTIVE empty-state alert when dataSource is undefined', () => {
		expect(getConnectorConnectionStatusAlert(undefined, 0)).toEqual({
			displayType: 'warning',
			message:
				'Your token was generated successfully. Complete your data source configuration to start syncing data.'
		});
	});
});
