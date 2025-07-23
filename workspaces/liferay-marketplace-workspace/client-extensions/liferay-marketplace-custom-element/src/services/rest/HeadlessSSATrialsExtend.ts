/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from '../../utils/axios';
import fetcher from '../fetcher';

export default class HeadlessSSATrialsExtend {
	static async createSSATrialsExtend(body: unknown) {
		const response = await axios.post('o/c/ssatrialextends', body);

		return response.data;
	}

	static getSSATrialsExtend(params = new URLSearchParams()) {
		return fetcher<APIResponse<TrialExtend>>(
			`/o/c/ssatrialextends?${params}`
		);
	}

	static async updateSSATrialsExtend(
		id: number | string,
		data: Partial<TrialExtend>
	) {
		return fetcher.patch(`o/c/ssatrialextends/${id}`, data);
	}
}
