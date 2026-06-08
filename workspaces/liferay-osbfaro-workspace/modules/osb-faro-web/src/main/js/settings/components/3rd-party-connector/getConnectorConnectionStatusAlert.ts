/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DataSource} from '~/shared/util/records';

import {getConnectorStatus} from './getConnectorStatus';
import {ConnectorStatus} from './types';

export interface ConnectorConnectionStatusAlert {
	displayType: 'success' | 'warning';
	message: string;
}

export function getConnectorConnectionStatusAlert(
	dataSource: DataSource | undefined,
	entityCount: number
): ConnectorConnectionStatusAlert {
	const status = getConnectorStatus(dataSource);

	if (status === ConnectorStatus.Disconnected) {
		return {
			displayType: 'warning',
			message: Liferay.Language.get(
				'data-is-no-longer-being-received-reconnect-to-resume-syncing'
			),
		};
	}

	if (status === ConnectorStatus.Active) {
		if (entityCount > 0) {
			return {
				displayType: 'success',
				message: Liferay.Language.get(
					'all-data-coming-from-this-data-source-is-up-to-date.-there-are-no-errors-to-report'
				),
			};
		}

		return {
			displayType: 'warning',
			message: Liferay.Language.get(
				'you-have-successfully-connected-to-your-data-source-complete-your-data-source-configuration-to-start-syncing-data'
			),
		};
	}

	return {
		displayType: 'warning',
		message:
			entityCount > 0
				? Liferay.Language.get(
						'data-is-no-longer-being-received-review-your-data-source-configuration-to-confirm-it-is-still-active'
					)
				: Liferay.Language.get(
						'your-token-was-generated-successfully-complete-your-data-source-configuration-to-start-syncing-data'
					),
	};
}
