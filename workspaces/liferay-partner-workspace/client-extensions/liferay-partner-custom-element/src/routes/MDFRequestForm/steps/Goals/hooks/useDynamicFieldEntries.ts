/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {LiferayPicklistName} from '../../../../../common/enums/liferayPicklistName';
import useGetListTypeDefinitions from '../../../../../common/services/liferay/list-type-definitions/useGetListTypeDefinitions';
import useGetMyUserAccount from '../../../../../common/services/liferay/user-account/useGetMyUserAccount';
import getEntriesByListTypeDefinitions from '../../../../../common/utils/getEntriesByListTypeDefinitions';

export default function useDynamicFieldEntries(skipCompanies?: boolean) {
	const {data: userAccount} = useGetMyUserAccount(skipCompanies);

	const {data: listTypeDefinitions} = useGetListTypeDefinitions([
		LiferayPicklistName.ADDITIONAL_OPTIONS,
		LiferayPicklistName.COUNTRIES,
		LiferayPicklistName.LIFERAY_BUSINESS_SALES_GOALS,
		LiferayPicklistName.TARGET_AUDIENCE_ROLES,
		LiferayPicklistName.TARGET_MARKETS,
		LiferayPicklistName.CURRENCIES,
	]);

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
