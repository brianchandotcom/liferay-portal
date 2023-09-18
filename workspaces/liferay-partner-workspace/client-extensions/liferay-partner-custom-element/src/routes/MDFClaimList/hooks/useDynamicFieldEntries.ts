/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {LiferayPicklistName} from '../../../common/enums/liferayPicklistName';
import useGetListTypeDefinitions from '../../../common/services/liferay/list-type-definitions/useGetListTypeDefinitions';
import useGetMyUserAccount from '../../../common/services/liferay/user-account/useGetMyUserAccount';
import getEntriesByListTypeDefinitions from '../../../common/utils/getEntriesByListTypeDefinitions';

export default function useDynamicFieldEntries() {
	const {data: userAccount} = useGetMyUserAccount();
	const {data: listTypeDefinitions} = useGetListTypeDefinitions([
		LiferayPicklistName.MDF_CLAIM_STATUS,
	]);

	const companiesEntries = useMemo(
		() =>
			userAccount?.accountBriefs.map((accountBrief) => ({
				label: accountBrief.name,
				value: accountBrief.id,
			})) as React.OptionHTMLAttributes<HTMLOptionElement>[],
		[userAccount?.accountBriefs]
	);

	const fieldEntries = useMemo(
		() => getEntriesByListTypeDefinitions(listTypeDefinitions?.items),
		[listTypeDefinitions?.items]
	);

	return {
		companiesEntries,
		fieldEntries,
	};
}
