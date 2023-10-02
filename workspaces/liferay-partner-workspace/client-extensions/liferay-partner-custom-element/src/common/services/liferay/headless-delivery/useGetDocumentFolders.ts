/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '..';
import useSWR from 'swr';

import LiferayDocumentFolder from '../../../interfaces/liferayDocumentFolder';
import {LiferayAPIs} from '../common/enums/apis';
import LiferayItems from '../common/interfaces/liferayItems';
import liferayFetcher from '../common/utils/fetcher';

export default function useGetDocumentFolder(siteId: number, name?: string) {
	return useSWR(
		[
			`/o/${
				LiferayAPIs.HEADERLESS_DELIVERY
			}/sites/${siteId}/document-folders/${
				name ? `?filter=name eq '${name}'` : ''
			}`,
			Liferay.authToken,
		],
		(url, token) =>
			liferayFetcher<LiferayItems<LiferayDocumentFolder[]>>(url, token)
	);
}
