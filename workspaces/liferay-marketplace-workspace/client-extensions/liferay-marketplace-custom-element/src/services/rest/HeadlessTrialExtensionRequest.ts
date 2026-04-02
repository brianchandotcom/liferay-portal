/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../fetcher';

export default class HeadlessTrialExtensionRequest {
	static async createTrialExtensionRequest(body: Omit<TrialExtend, 'id'>) {
		return fetcher.post('o/c/trialextensionrequests', body);
	}

	static getTrialExtensionRequest(params = new URLSearchParams()) {
		return fetcher<APIResponse<TrialExtend>>(
			`/o/c/trialextensionrequests?${params}`
		);
	}

	static async updateTrialExtensionRequest(
		id: number | string,
		data: Partial<TrialExtend>
	) {
		return fetcher.patch(`o/c/trialextensionrequests/${id}`, data);
	}
}
