/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {Liferay} from '../../../components/dashboard/utils/liferay';
import liferayFetcher from '../common/utils/fetcher';

export default function useGet<T>(urlGet?: string, maxRetryCount: number = 5) {
	return useSWR(
		urlGet ? [urlGet, Liferay.authToken] : null,
		(url, token) => liferayFetcher<T>(url, token),
		{
			onErrorRetry: (_error, _key, _config, revalidate, {retryCount}) => {
				if (retryCount >= maxRetryCount) {
					return;
				}

				setTimeout(() => revalidate({retryCount}), 400 * retryCount);
			},
		}
	);
}
