/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '..';
import useSWR from 'swr';

import {LiferayPicklistName} from '../../../enums/liferayPicklistName';
import ListTypeDefinition from '../../../interfaces/listTypeDefinition';
import {LiferayAPIs} from '../common/enums/apis';
import LiferayItems from '../common/interfaces/liferayItems';
import liferayFetcher from '../common/utils/fetcher';

export default function useGetListTypeDefinitions(
	names: LiferayPicklistName[]
) {
	return useSWR(
		[
			`/o/${
				LiferayAPIs.HEADERLESS_ADMIN_LIST_TYPE
			}/list-type-definitions?filter=name in ('${names.join("', '")}')`,
			Liferay.authToken,
		],
		(url, token) =>
			liferayFetcher<LiferayItems<ListTypeDefinition[]>>(url, token)
	);
}
