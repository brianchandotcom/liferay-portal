/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from '../../utils/axios';
import fetcher from '../fetcher';

export default class HeadlessAppPackage {
	static async createAppPackage(body: unknown) {
		const response = await axios.post('o/c/apppackages', body);

		return response.data;
	}

	static async deleteAppPackage(id: number | string) {
		return fetcher.delete(`o/c/apppackages/${id}`);
	}

	static getAppPackages(searchParams: URLSearchParams) {
		return fetcher<APIResponse>(
			`o/c/apppackages?${searchParams.toString()}`
		);
	}
}
