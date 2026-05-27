/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import fetcher from '../services/fetcher';

const useListTypeDefinition = (externalReferenceCode: string | null) => {
	return useSWR(
		externalReferenceCode
			? `/list-type-definition/${externalReferenceCode}`
			: null,
		() =>
			fetcher<ListTypeDefinition>(
				`o/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/${externalReferenceCode}`
			)
	);
};

export default useListTypeDefinition;
