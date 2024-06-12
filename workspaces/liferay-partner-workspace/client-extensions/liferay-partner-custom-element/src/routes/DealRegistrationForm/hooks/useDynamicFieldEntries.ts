/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo} from 'react';

import {LiferayPicklistName} from '../../../common/enums/liferayPicklistName';
import ListTypeDefinition from '../../../common/interfaces/listTypeDefinition';
import UserAccount from '../../../common/interfaces/userAccount';
import {LiferayAPIs} from '../../../common/services/liferay/common/enums/apis';
import LiferayItems from '../../../common/services/liferay/common/interfaces/liferayItems';
import useGet from '../../../common/services/liferay/object/useGet';
import getEntriesByListTypeDefinitions from '../../../common/utils/getEntriesByListTypeDefinitions';

export default function useDynamicFieldEntries(
	handleSelected: (
		firstName?: string,
		lastName?: string,
		emailAddress?: string,
		telephone?: string
	) => void
) {
	const {data: userAccount} = useGet<UserAccount>(
		`/o/${LiferayAPIs.HEADERLESS_ADMIN_USER}/my-user-account`
	);

	const {data: listTypeDefinitions} = useGet<
		LiferayItems<ListTypeDefinition[]>
	>(
		`/o/${
			LiferayAPIs.HEADERLESS_ADMIN_LIST_TYPE
		}/list-type-definitions?filter=name in ('${[
			LiferayPicklistName.COUNTRIES,
			LiferayPicklistName.STATES,
			LiferayPicklistName.PROJECT_CATEGORIES,
			LiferayPicklistName.PROJECT_INFORMATIONS,
			LiferayPicklistName.JOB_ROLES,
			LiferayPicklistName.DEPARTMENTS,
			LiferayPicklistName.INDUSTRIES,
			LiferayPicklistName.STATES,
			LiferayPicklistName.CURRENCIES,
		].join("', '")}')`
	);

	const companiesEntries = useMemo(
		() =>
			userAccount?.accountBriefs.map((accountBrief) => ({
				label: accountBrief.name,
				value: accountBrief.externalReferenceCode,
			})) as React.OptionHTMLAttributes<HTMLOptionElement>[],
		[userAccount?.accountBriefs]
	);

	const partnerPrimaryPhone =
		userAccount?.userAccountContactInformation?.telephones?.find(
			(phoneNumber) => phoneNumber.primary
		);

	useEffect(() => {
		if (
			userAccount?.givenName ||
			(userAccount?.familyName && userAccount?.emailAddress) ||
			partnerPrimaryPhone?.phoneNumber
		) {
			handleSelected(
				userAccount?.givenName,
				userAccount?.familyName,
				userAccount?.emailAddress,
				partnerPrimaryPhone?.phoneNumber
			);
		}
	}, [
		handleSelected,
		userAccount?.familyName,
		userAccount?.givenName,
		userAccount?.emailAddress,
		partnerPrimaryPhone?.phoneNumber,
	]);

	const fieldEntries = useMemo(
		() => getEntriesByListTypeDefinitions(listTypeDefinitions?.items),
		[listTypeDefinitions?.items]
	);

	return {
		companiesEntries,
		fieldEntries,
	};
}
