/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {Liferay} from './../services/liferay';

const useListTypeDefinition = (externalReferenceCode: string) => {
	return useSWR(
		`/list-type-definition/${externalReferenceCode}`,
		async () => {
			const response = await Liferay.Util.fetch(
				`/o/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/${externalReferenceCode}`
			);

			const data = await response.json();

			return data.listTypeEntries;
		}
	);
};

export default useListTypeDefinition;
