/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {LiferayPicklistName} from '../../../../../../../common/enums/liferayPicklistName';
import ListTypeDefinition from '../../../../../../../common/interfaces/listTypeDefinition';
import {LiferayAPIs} from '../../../../../../../common/services/liferay/common/enums/apis';
import LiferayItems from '../../../../../../../common/services/liferay/common/interfaces/liferayItems';
import useGet from '../../../../../../../common/services/liferay/object/useGet';
import getEntriesByListTypeDefinitions from '../../../../../../../common/utils/getEntriesByListTypeDefinitions';

export default function useDynamicFieldEntries() {
	const {data: listTypeDefinitions} = useGet<
		LiferayItems<ListTypeDefinition[]>
	>(
		`/o/${
			LiferayAPIs.HEADERLESS_ADMIN_LIST_TYPE
		}/list-type-definitions?filter=name in ('${[
			LiferayPicklistName.LEAD_FOLLOW_UP_STRATEGIES,
			LiferayPicklistName.BUDGET_EXPENSES,
			LiferayPicklistName.TYPE_OF_ACTIVITY,
			LiferayPicklistName.TACTIC,
		].join("', '")}')`
	);

	const fieldEntries = useMemo(
		() => getEntriesByListTypeDefinitions(listTypeDefinitions?.items),
		[listTypeDefinitions?.items]
	);

	return {
		fieldEntries,
	};
}
