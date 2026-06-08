/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetchConnectorEntityCount} from '~/shared/api/connector';
import {DataSourceTypes} from '~/shared/util/constants';

import {buildLanguages} from '../buildLanguages';
import {ConnectorConfig, Entity} from '../types';

const SLUG = 'demandbase';

const displayName = Liferay.Language.get('demandbase');

const demandbaseConfig: ConnectorConfig = {
	displayName,
	endpointPath: '/api/demandbase_accounts',
	entities: [
		{
			entity: Entity.Accounts,
			fetchCount: (params) =>
				fetchConnectorEntityCount(Entity.Accounts, params),
		},
	],
	languages: buildLanguages(displayName),
	requiresLDP: true,
	singleton: true,
	slug: SLUG,
	type: DataSourceTypes.Demandbase,
};

export default demandbaseConfig;
