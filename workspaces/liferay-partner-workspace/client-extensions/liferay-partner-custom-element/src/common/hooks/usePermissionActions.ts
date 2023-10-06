/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import PermissionAction from '../interfaces/permissionAction';
import {LiferayAPIs} from '../services/liferay/common/enums/apis';
import LiferayItems from '../services/liferay/common/interfaces/liferayItems';
import useGet from '../services/liferay/object/useGet';

export default function usePermissionActions(objectName: string) {
	const response = useGet<LiferayItems<PermissionAction[]>>(
		`/o/${LiferayAPIs.OBJECT}/permissionactions?filter=object eq '${objectName}'&pageSize=-1`
	);

	return useMemo(
		() =>
			response.data?.items.map(
				(permissionAction) => permissionAction.action
			),
		[response.data?.items]
	);
}
