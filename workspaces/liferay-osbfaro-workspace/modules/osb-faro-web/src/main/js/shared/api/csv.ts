/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CSVType} from '~/shared/components/download-report/utils';
import sendRequest from '~/shared/util/request';

export const fetchCSV = function fetchCSV(url: string) {
	return fetch(url);
};

export const fetchCount = function fetchCount({
	groupId,
	type,
	...data
}: {
	assetId?: string;
	assetType?: string;
	channelId: string;
	fromDate?: string;
	groupId: string;
	individualId?: string;
	rangeKey?: string;
	segmentId?: string;
	toDate?: string;
	type: CSVType;
}) {
	return sendRequest({
		data,
		method: 'GET',
		path: `main/${groupId}/reports/export/csv/${type}/count`,
	});
};
