/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from '../../utils/axios';

export default class HeadlessProductFeedback {
	static async createProductFeedback(body: unknown) {
		const response = await axios.post('o/c/productfeedbacks', body);

		return response.data;
	}
}
