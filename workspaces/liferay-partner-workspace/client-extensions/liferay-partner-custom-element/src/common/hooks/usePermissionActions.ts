/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import useGetPermissionActions from '../services/liferay/object/permission-action/useGetPermissionActions';

export default function usePermissionActions(objectName: string) {
	const response = useGetPermissionActions(objectName);

	return useMemo(
		() =>
			response.data?.items.map(
				(permissionAction) => permissionAction.action
			),
		[response.data?.items]
	);
}
