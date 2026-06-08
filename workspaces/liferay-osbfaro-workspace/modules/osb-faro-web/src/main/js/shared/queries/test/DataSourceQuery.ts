/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DataSourceQuery from '../DataSourceQuery';

describe('DataSourceQuery', () => {
	it('does not include credentialsType', () => {
		const queryString =

			// eslint-disable-next-line @typescript-eslint/no-explicit-any
			(DataSourceQuery as any).loc?.source?.body ?? '';

		expect(queryString).not.toContain('credentialsType');
	});
});
