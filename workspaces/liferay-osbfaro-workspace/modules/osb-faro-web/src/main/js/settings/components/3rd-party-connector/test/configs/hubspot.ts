/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetchConnectorEntityCount} from '~/shared/api/connector';
import {DataSourceTypes} from '~/shared/util/constants';

import hubspotConfig from '../../configs/hubspot';
import {Entity} from '../../types';

jest.mock('~/shared/api/connector', () => ({
	fetchConnectorEntityCount: jest.fn(() => Promise.resolve(123)),
}));

describe('hubspot config', () => {
	beforeEach(() => {
		(fetchConnectorEntityCount as jest.Mock).mockClear();
	});

	it('uses the hubspot slug', () => {
		expect(hubspotConfig.slug).toBe('hubspot');
	});

	it('uses the hubspot data source type enum', () => {
		expect(hubspotConfig.type).toBe(DataSourceTypes.Hubspot);
	});

	it('targets the hubspot webhooks endpoint', () => {
		expect(hubspotConfig.endpointPath).toBe('/api/hubspot_webhooks');
	});

	it('is not flagged as singleton (multiple instances allowed)', () => {
		expect(hubspotConfig.singleton).toBe(true);
	});

	it('exposes the Events entity', () => {
		expect(hubspotConfig.entities).toHaveLength(1);

		const [events] = hubspotConfig.entities;

		expect(events.entity).toBe(Entity.Events);
		expect(typeof events.fetchCount).toBe('function');
	});

	it('delegates fetchCount to fetchConnectorEntityCount with the entity', async () => {
		await hubspotConfig.entities[0].fetchCount!({
			groupId: '23',
			id: 'data-source-1',
		});

		expect(fetchConnectorEntityCount).toHaveBeenCalledWith(Entity.Events, {
			groupId: '23',
			id: 'data-source-1',
		});
	});

	it('builds the language strings from the configured display name', () => {
		expect(hubspotConfig.languages.connectTitle).toContain(
			hubspotConfig.displayName
		);
		expect(hubspotConfig.languages.tokenLabel).toContain(
			hubspotConfig.displayName
		);
	});
});
