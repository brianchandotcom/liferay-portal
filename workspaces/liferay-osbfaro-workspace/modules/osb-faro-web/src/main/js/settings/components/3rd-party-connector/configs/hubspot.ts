/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetchConnectorEntityCount} from '~/shared/api/connector';
import {DataSourceTypes} from '~/shared/util/constants';

import {buildLanguages} from '../buildLanguages';
import {ConnectorConfig, Entity} from '../types';

const SLUG = 'hubspot';

const displayName = Liferay.Language.get('hubspot');

const hubspotConfig: ConnectorConfig = {
	displayName,
	endpointPath: '/api/hubspot_webhooks',
	entities: [
		{
			entity: Entity.Events,
			fetchCount: (params) =>
				fetchConnectorEntityCount(Entity.Events, params),
		},
	],
	languages: buildLanguages(displayName),
	requiresLDP: true,
	singleton: true,
	slug: SLUG,
	type: DataSourceTypes.Hubspot,
};

export default hubspotConfig;
