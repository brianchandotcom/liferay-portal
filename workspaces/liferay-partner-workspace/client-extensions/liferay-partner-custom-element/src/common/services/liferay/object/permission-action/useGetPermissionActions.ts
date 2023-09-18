/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {Liferay} from '../..';
import PermissionAction from '../../../../interfaces/permissionAction';
import {LiferayAPIs} from '../../common/enums/apis';
import LiferayItems from '../../common/interfaces/liferayItems';
import liferayFetcher from '../../common/utils/fetcher';

export default function useGetPermissionActions(ObjectName: string) {
	return useSWR(
		[
			`/o/${LiferayAPIs.OBJECT}/permissionactions?filter=object eq '${ObjectName}'&pageSize=-1`,
			Liferay.authToken,
		],
		(url, token) =>
			liferayFetcher<LiferayItems<PermissionAction[]>>(url, token)
	);
}
