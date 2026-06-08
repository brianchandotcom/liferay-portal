/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DataSourceStates, DataSourceStatuses} from '~/shared/util/constants';
import {DataSource} from '~/shared/util/records';

import {ConnectorStatus} from './types';

export function getConnectorStatus(dataSource?: DataSource): ConnectorStatus {
	if (dataSource?.state === DataSourceStates.Disconnected) {
		return ConnectorStatus.Disconnected;
	}

	if (dataSource?.status === DataSourceStatuses.Active) {
		return ConnectorStatus.Active;
	}

	return ConnectorStatus.Inactive;
}
