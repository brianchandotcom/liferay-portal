/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from '~/shared/util/lang';

import {Languages} from './types';

export function buildLanguages(displayName: string): Languages {
	return {
		connectDescription: sub(
			Liferay.Language.get(
				'connect-your-x-account-to-analytics-cloud-to-start-importing-data'
			),
			[displayName]
		) as string,
		connectTitle: sub(Liferay.Language.get('connect-x'), [
			displayName,
		]) as string,
		endpointHelper: sub(
			Liferay.Language.get(
				'this-is-analytics-cloud-url-x-will-redirect-to-after-a-user-authorizes-the-connection'
			),
			[displayName]
		) as string,
		endpointLabel: sub(
			Liferay.Language.get('copy-this-endpoint-url-to-your-x-instance'),
			[displayName]
		) as string,
		tokenLabel: sub(
			Liferay.Language.get('copy-this-token-to-your-x-instance'),
			[displayName]
		) as string,
	};
}
