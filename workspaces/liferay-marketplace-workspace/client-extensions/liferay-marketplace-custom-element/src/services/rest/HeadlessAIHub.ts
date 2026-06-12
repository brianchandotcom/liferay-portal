/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../fetcher';

export default class HeadlessAIHub {
	static async getAIHubApplicationByExternalReferenceCode(
		externalReferenceCode: string
	) {
		return fetcher(
			`o/c/aihubapplications/by-external-reference-code/AI-HUB-${externalReferenceCode}`
		);
	}
}
