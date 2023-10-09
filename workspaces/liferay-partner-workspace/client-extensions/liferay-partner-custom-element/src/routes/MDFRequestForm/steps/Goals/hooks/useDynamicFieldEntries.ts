/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {LiferayPicklistName} from '../../../../../common/enums/liferayPicklistName';
import ListTypeDefinition from '../../../../../common/interfaces/listTypeDefinition';
import UserAccount from '../../../../../common/interfaces/userAccount';
import {LiferayAPIs} from '../../../../../common/services/liferay/common/enums/apis';
import LiferayItems from '../../../../../common/services/liferay/common/interfaces/liferayItems';
import useGet from '../../../../../common/services/liferay/object/useGet';
import getEntriesByListTypeDefinitions from '../../../../../common/utils/getEntriesByListTypeDefinitions';

export default function useDynamicFieldEntries(skipCompanies?: boolean) {
	const {data: userAccount} = useGet<UserAccount>(
		skipCompanies
			? ``
			: `/o/${LiferayAPIs.HEADERLESS_ADMIN_USER}/my-user-account`
	);

	const {data: listTypeDefinitions} = useGet<
		LiferayItems<ListTypeDefinition[]>
	>(
		`/o/${
			LiferayAPIs.HEADERLESS_ADMIN_LIST_TYPE
		}/list-type-definitions?filter=name in ('${[
			LiferayPicklistName.ADDITIONAL_OPTIONS,
			LiferayPicklistName.COUNTRIES,
			LiferayPicklistName.LIFERAY_BUSINESS_SALES_GOALS,
			LiferayPicklistName.TARGET_AUDIENCE_ROLES,
			LiferayPicklistName.TARGET_MARKETS,
			LiferayPicklistName.CURRENCIES,
		].join("', '")}')`
	);

	const companiesEntries = useMemo(
		() =>
			userAccount?.accountBriefs.map((accountBrief) => ({
				defaultValue: accountBrief.id,
				label: accountBrief.name,
				value: accountBrief.externalReferenceCode,
			})) as React.OptionHTMLAttributes<HTMLOptionElement>[] | undefined,
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
