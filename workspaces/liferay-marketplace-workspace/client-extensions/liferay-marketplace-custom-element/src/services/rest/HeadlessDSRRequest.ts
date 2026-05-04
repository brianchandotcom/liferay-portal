/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../fetcher';

export default class HeadlessDSRRequest {
	static async putDSRRequest(body: any, externalReferenceCode: string) {
		return fetcher.put(
			`o/c/dsrrequests/by-external-reference-code/${externalReferenceCode}`,
			body
		);
	}
}
