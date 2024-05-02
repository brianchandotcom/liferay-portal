/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface URLParamsProps {
	filter: string;
	nestedFields?: string;
	sort?: string;
	urlParams: URLSearchParams;
}

export default function setURLParams({
	filter,
	nestedFields,
	sort,
	urlParams,
}: URLParamsProps) {
	if (nestedFields) {
		urlParams.set('nestedFields', nestedFields);
	}

	if (sort) {
		urlParams.set('sort', sort);
	}

	urlParams.set('filter', filter);

	return urlParams;
}
