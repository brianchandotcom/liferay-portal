/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fromJS} from 'immutable';
import {noop, range} from 'lodash';
import * as API from '~/shared/api';
import {DataSourceStates, DataSourceStatuses} from '~/shared/util/constants';
import {DataSource} from '~/shared/util/records';
import {Routes, toRoute} from '~/shared/util/router';
import * as data from '~/test/data';

import {
	STATUS_DISPLAY,
	dataSourceRedirectFn,
	getDataSourceDisplayObject,
	getIdsFromConfiguration,
	getServiceAlertConfig,
	isDataSourceValid,
	validAnalyticsConfig,
	validContactsConfig,
	validateUniqueName,
} from '../data-sources';

function getMockLiferayDataSource(id, config) {
	return data.getImmutableMock(
		DataSource,
		data.mockLiferayDataSource,
		id,
		config
	);
}

describe('data-sources', () => {
	describe('dataSourceRedirectFn', () => {
		it('returns the SETTINGS_DATA_SOURCE route if the data source state is NOT VALID', () => {
			const groupId = '23';
			const id = '123';

			const result = dataSourceRedirectFn({
				dataSource: getMockLiferayDataSource(id, {
					state: DataSourceStates.UrlInvalid,
				}),
				groupId,
			});

			expect(result).toBe(
				toRoute(Routes.SETTINGS_DATA_SOURCE, {groupId, id})
			);
		});

		it('returns null if the data source state is VALID', () => {
			const groupId = '23';
			const id = '123';

			const result = dataSourceRedirectFn({
				dataSource: getMockLiferayDataSource(id, {
					state: DataSourceStates.Ready,
				}),
				groupId,
			});

			expect(result).toBe(null);
		});
	});

	describe('isDataSourceValid', () => {
		it.each`
			state                                     | isValid
			${DataSourceStates.CredentialsInvalid}    | ${false}
			${DataSourceStates.CredentialsValid}      | ${true}
			${DataSourceStates.LiferayVersionInvalid} | ${false}
			${DataSourceStates.Ready}                 | ${true}
			${DataSourceStates.UndefinedError}        | ${false}
			${DataSourceStates.UrlInvalid}            | ${false}
			${null}                                   | ${false}
		`(
			'should return whether the datasource state is considered valid',
			({isValid, state}) => {
				const result = isDataSourceValid(state);

				expect(result).toEqual(isValid);
			}
		);
	});

	describe('getDataSourceDisplayObject', () => {
		it('returns the active state display object if credentials are valid, at least one configuration is valid, and active is true', () => {
			const result = getDataSourceDisplayObject(
				getMockLiferayDataSource(1, {
					provider: {
						contactsConfiguration: {enableAllContacts: true},
					},
					state: DataSourceStates.CredentialsValid,
				})
			);

			expect(result).toEqual(STATUS_DISPLAY.active);
		});

		it('returns the active display object if credentials are valid, and only analytics are configured', () => {
			const result = getDataSourceDisplayObject(
				getMockLiferayDataSource(1, {
					provider: {
						analyticsConfiguration: {sites: [{id: '1'}]},
					},
					state: DataSourceStates.CredentialsValid,
					status: DataSourceStatuses.Inactive,
				})
			);

			expect(result).toEqual(STATUS_DISPLAY.active);
		});

		it('returns the authenticated state display object if credentials are valid but active is false', () => {
			const result = getDataSourceDisplayObject(
				getMockLiferayDataSource(1, {
					state: DataSourceStates.CredentialsValid,
					status: DataSourceStatuses.Inactive,
				})
			);

			expect(result).toEqual(
				STATUS_DISPLAY[DataSourceStates.CredentialsValid]
			);
		});

		it('returns the invalid credentials state display object if credentials are invalid', () => {
			const result = getDataSourceDisplayObject(
				getMockLiferayDataSource(1, {
					state: DataSourceStates.CredentialsInvalid,
				})
			);

			expect(result).toEqual(
				STATUS_DISPLAY[DataSourceStates.CredentialsInvalid]
			);
		});

		it('returns the invalid credentials state display object if the state is ANALYTICS_CLIENT_CONFIGURATION_FAILURE', () => {
			const result = getDataSourceDisplayObject(
				getMockLiferayDataSource(1, {
					state: DataSourceStates.AnalyticsClientConfigurationFailure,
				})
			);

			expect(result).toEqual(
				STATUS_DISPLAY[DataSourceStates.CredentialsInvalid]
			);
		});

		it('returns the "in-progress deletion" state display object if the data source state is in deletion', () => {
			const result = getDataSourceDisplayObject(
				getMockLiferayDataSource(1, {
					state: DataSourceStates.InProgressDeleting,
				})
			);

			expect(result).toEqual(
				STATUS_DISPLAY[DataSourceStates.InProgressDeleting]
			);
		});

		it('returns the not configured state display object if the state does match any of the state types', () => {
			const result = getDataSourceDisplayObject(
				getMockLiferayDataSource(1, {
					state: undefined,
					status: DataSourceStatuses.Inactive,
				})
			);

			expect(result).toEqual(STATUS_DISPLAY.default);
		});

		it('returns the undefined error state display object if the state is UNDEFINED_ERROR', () => {
			const result = getDataSourceDisplayObject(
				getMockLiferayDataSource(1, {
					state: DataSourceStates.UndefinedError,
					status: DataSourceStatuses.Inactive,
				})
			);

			expect(result).toMatchObject({
				display: 'warning',
				label: 'Inactive',
			});
			expect(Array.isArray(result.message)).toBe(true);
		});

		it('returns the disconnected state display object if the state is DISCONNECTED', () => {
			const result = getDataSourceDisplayObject(
				getMockLiferayDataSource(1, {
					state: DataSourceStates.Disconnected,
					status: DataSourceStatuses.Inactive,
				})
			);

			expect(result).toMatchObject({
				display: 'secondary',
				label: 'Disconnected',
			});
		});
	});

	describe('getIdsFromConfiguration', () => {
		it('returns an array of numerical ids from a Map.<List.<Map>>', () => {
			const expectedArray = range(5);

			const testKey = 'testKey';

			const mockMap = fromJS({
				[testKey]: expectedArray.map((i) => ({id: i})),
			});

			expect(getIdsFromConfiguration(mockMap, testKey)).toEqual(
				expectedArray
			);
		});
	});

	describe('getServiceAlertConfig', () => {
		it('returns a service permission related alert props', () => {
			const result = getServiceAlertConfig(403);

			expect(result).toMatchObject({
				alertType: 'WARNING',
				timeout: 7000,
			});
			expect(typeof result.message).toBe('string');
			expect(result.message.length).toBeGreaterThan(0);
		});

		it('returns a service unresponsive related alert props', () => {
			const result = getServiceAlertConfig(404);

			expect(result).toMatchObject({
				alertType: 'WARNING',
				timeout: 7000,
			});
			expect(typeof result.message).toBe('string');
			expect(result.message.length).toBeGreaterThan(0);
		});
	});

	describe('validateUniqueName', () => {
		it('returns a success assertion if the data source name does NOT already exist', () => {
			expect.assertions(1);

			const uniqueDataSourceName = 'barbaz';

			API.dataSource.search.mockReturnValueOnce(
				Promise.resolve(data.mockSearch(noop, 0))
			);

			return validateUniqueName({
				groupId: '23',
				value: uniqueDataSourceName,
			}).then((result) => {
				expect(result).toBe('');
			});
		});

		it('returns a failure assertion if the data source name already exists', () => {
			expect.assertions(1);

			const existingDataSourceName = 'foo';

			API.dataSource.search.mockReturnValueOnce(
				Promise.resolve(
					data.mockSearch(data.mockLiferayDataSource, 1, [
						{
							name: existingDataSourceName,
						},
					])
				)
			);

			return expect(
				validateUniqueName({
					groupId: '23',
					value: `${existingDataSourceName}1`,
				})
			).resolves.toEqual(
				'A Data Source already exists with that name. Please enter a different name.'
			);
		});
	});

	describe('validAnalyticsConfig', () => {
		it('returns false for csv data source types since they only have contacts', () => {
			const mockCSVDataSource = data.getImmutableMock(
				DataSource,
				data.mockCSVDataSource,
				1
			);

			expect(validAnalyticsConfig(mockCSVDataSource)).toBe(false);
		});

		it('returns be able to determine if a liferay data source has a valid analyticsConfiguration', () => {
			const withValidAnalytics = getMockLiferayDataSource(1, {
				provider: {
					analyticsConfiguration: {sites: [{id: '1'}]},
				},
			});

			const withInvalidAnalytics = getMockLiferayDataSource(1);

			expect(validAnalyticsConfig(withValidAnalytics)).toBe(true);

			expect(validAnalyticsConfig(withInvalidAnalytics)).toBe(false);
		});

		it('returns be able to determine if a salesforce data source has a valid analyticsConfiguration', () => {
			const withInvalidAnalytics = data.getImmutableMock(
				DataSource,
				data.mockSalesforceDataSource,
				1
			);

			expect(validAnalyticsConfig(withInvalidAnalytics)).toBe(false);
		});
	});

	describe('validContactsConfig', () => {
		it('returns true for csv types if the status is ACTIVE', () => {
			const mockCSVDataSource = data.getImmutableMock(
				DataSource,
				data.mockCSVDataSource,
				1
			);

			expect(validContactsConfig(mockCSVDataSource)).toBe(true);
		});

		it('returns be able to determine if a liferay data source has a valid contactsConfiguration', () => {
			const withValidContacts = getMockLiferayDataSource(1, {
				provider: {
					contactsConfiguration: {enableAllContacts: true},
				},
			});

			const withInvalidContacts = getMockLiferayDataSource(1);

			expect(validContactsConfig(withValidContacts)).toBe(true);

			expect(validContactsConfig(withInvalidContacts)).toBe(false);
		});

		it('returns be able to determine if a salesforce data source has a valid contactsConfiguration', () => {
			const withInvalidContacts = data.getImmutableMock(
				DataSource,
				data.mockSalesforceDataSource,
				1
			);

			expect(validContactsConfig(withInvalidContacts)).toBe(false);
		});
	});
});
