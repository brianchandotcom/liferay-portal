/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../fetcher';

export default class HeadlessPublisherAssetAttachment {
	static async createPublisherAssetAttachment(body: unknown) {
		return fetcher.post('o/c/publisherassetattachments', body);
	}

	static async deletePublisherAssetAttachment(id: number | string) {
		return fetcher.delete(`o/c/publisherassetattachments/${id}`);
	}

	static getPublisherAssetAttachments(searchParams: URLSearchParams) {
		return fetcher<APIResponse>(
			`o/c/publisherassetattachments?${searchParams.toString()}`
		);
	}
}
