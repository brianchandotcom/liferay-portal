/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {LiferayPicklistName} from '../../../../../../../common/enums/liferayPicklistName';
import useGetListTypeDefinitions from '../../../../../../../common/services/liferay/list-type-definitions/useGetListTypeDefinitions';
import getEntriesByListTypeDefinitions from '../../../../../../../common/utils/getEntriesByListTypeDefinitions';

export default function useDynamicFieldEntries() {
	const {data: listTypeDefinitions} = useGetListTypeDefinitions([
		LiferayPicklistName.LEAD_FOLLOW_UP_STRATEGIES,
		LiferayPicklistName.BUDGET_EXPENSES,
		LiferayPicklistName.TYPE_OF_ACTIVITY,
		LiferayPicklistName.TACTIC,
	]);

	const fieldEntries = useMemo(
		() => getEntriesByListTypeDefinitions(listTypeDefinitions?.items),
		[listTypeDefinitions?.items]
	);

	return {
		fieldEntries,
	};
}
