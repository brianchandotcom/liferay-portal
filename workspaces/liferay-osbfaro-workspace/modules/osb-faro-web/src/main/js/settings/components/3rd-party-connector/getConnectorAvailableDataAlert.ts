/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DataSource} from '~/shared/util/records';

import {getConnectorStatus} from './getConnectorStatus';
import {ConnectorStatus} from './types';

export type ConnectorAvailableDataAlertKind = 'previously-synced' | 'syncing';

export interface ConnectorAvailableDataAlert {
	displayType: 'info';
	kind: ConnectorAvailableDataAlertKind;
	message: string;
}

export function getConnectorAvailableDataAlert(
	dataSource: DataSource | undefined,
	hasData: boolean
): ConnectorAvailableDataAlert | null {
	const status = getConnectorStatus(dataSource);

	if (status === ConnectorStatus.Disconnected) {
		return {
			displayType: 'info',
			kind: 'previously-synced',
			message: Liferay.Language.get(
				'previously-synced-data-remains-available-reconnect-or-check-your-data-source-connection-to-resume-data-syncing'
			),
		};
	}

	if (status === ConnectorStatus.Inactive && hasData) {
		return {
			displayType: 'info',
			kind: 'previously-synced',
			message: Liferay.Language.get(
				'previously-synced-data-remains-available-reconnect-or-check-your-data-source-connection-to-resume-data-syncing'
			),
		};
	}

	if (status === ConnectorStatus.Active && hasData) {
		return {
			displayType: 'info',
			kind: 'syncing',
			message: Liferay.Language.get(
				'your-data-may-take-some-time-to-appear-as-syncing-completes'
			),
		};
	}

	return null;
}
