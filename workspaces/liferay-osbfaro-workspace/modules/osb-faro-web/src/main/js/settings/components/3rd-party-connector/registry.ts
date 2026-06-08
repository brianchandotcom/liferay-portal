/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DataSourceTypes} from '~/shared/util/constants';
import {isLDPPlan} from '~/shared/util/subscriptions';

// https://liferay.atlassian.net/wiki/spaces/ENGAC/pages/4841275420/Third-Party+Connector+Framework+-+Adding+a+New+Data+Source+Connector+on+osb-faro-web+frontend

import demandbaseConfig from './configs/demandbase';
import hubspotConfig from './configs/hubspot';
import marketoConfig from './configs/marketo';
import {ConnectorConfig} from './types';

const connectorRegistry: Record<string, ConnectorConfig> = {
	[DataSourceTypes.Demandbase]: demandbaseConfig,
	[DataSourceTypes.Hubspot]: hubspotConfig,
	[DataSourceTypes.Marketo]: marketoConfig,
};

export function getConnectorConfig(
	type: string | undefined
): ConnectorConfig | undefined {
	if (!type) {
		return undefined;
	}

	return connectorRegistry[type.toUpperCase()];
}

export function listConnectors(): ConnectorConfig[] {
	return Object.values(connectorRegistry);
}

export function listAvailableConnectors(
	existingTypes: ReadonlySet<string>,
	subscriptionName: string | null = null
): ConnectorConfig[] {
	const ldpAllowed = isLDPPlan(subscriptionName);

	return listConnectors().filter((config) => {
		if (config.singleton && existingTypes.has(config.type)) {
			return false;
		}

		if (config.requiresLDP && !ldpAllowed) {
			return false;
		}

		return true;
	});
}

export {connectorRegistry};
