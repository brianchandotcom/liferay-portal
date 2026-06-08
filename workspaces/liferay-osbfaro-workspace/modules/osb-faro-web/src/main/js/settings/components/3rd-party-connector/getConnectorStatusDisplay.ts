/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DataSource} from '~/shared/util/records';

import {getConnectorStatus} from './getConnectorStatus';
import {ConnectorStatus} from './types';

export interface ConnectorStatusDisplay {
	display: 'secondary' | 'success' | 'warning';
	label: string;
}

const STATUS_DISPLAY: Record<ConnectorStatus, ConnectorStatusDisplay> = {
	[ConnectorStatus.Active]: {
		display: 'success',
		label: Liferay.Language.get('active'),
	},
	[ConnectorStatus.Disconnected]: {
		display: 'secondary',
		label: Liferay.Language.get('disconnected'),
	},
	[ConnectorStatus.Inactive]: {
		display: 'warning',
		label: Liferay.Language.get('inactive'),
	},
};

export function getConnectorStatusDisplay(
	dataSource?: DataSource
): ConnectorStatusDisplay {
	return STATUS_DISPLAY[getConnectorStatus(dataSource)];
}
