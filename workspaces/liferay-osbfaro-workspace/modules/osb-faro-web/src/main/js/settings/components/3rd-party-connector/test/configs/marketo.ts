/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetchConnectorEntityCount} from '~/shared/api/connector';
import {DataSourceTypes} from '~/shared/util/constants';

import marketoConfig from '../../configs/marketo';
import {Entity} from '../../types';

jest.mock('~/shared/api/connector', () => ({
	fetchConnectorEntityCount: jest.fn(() => Promise.resolve(123)),
}));

describe('marketo config', () => {
	beforeEach(() => {
		(fetchConnectorEntityCount as jest.Mock).mockClear();
	});

	it('uses the marketo slug', () => {
		expect(marketoConfig.slug).toBe('marketo');
	});

	it('uses the marketo data source type enum', () => {
		expect(marketoConfig.type).toBe(DataSourceTypes.Marketo);
	});

	it('targets the marketo webhooks endpoint', () => {
		expect(marketoConfig.endpointPath).toBe('/api/marketo_webhooks');
	});

	it('is flagged as singleton', () => {
		expect(marketoConfig.singleton).toBe(true);
	});

	it('exposes the Events entity', () => {
		expect(marketoConfig.entities).toHaveLength(1);

		const [events] = marketoConfig.entities;

		expect(events.entity).toBe(Entity.Events);
		expect(typeof events.fetchCount).toBe('function');
	});

	it('delegates fetchCount to fetchConnectorEntityCount with the entity', async () => {
		await marketoConfig.entities[0].fetchCount!({
			groupId: '23',
			id: 'data-source-1',
		});

		expect(fetchConnectorEntityCount).toHaveBeenCalledWith(Entity.Events, {
			groupId: '23',
			id: 'data-source-1',
		});
	});

	it('builds the language strings from the configured display name', () => {
		expect(marketoConfig.languages.connectTitle).toContain(
			marketoConfig.displayName
		);
		expect(marketoConfig.languages.tokenLabel).toContain(
			marketoConfig.displayName
		);
	});
});
