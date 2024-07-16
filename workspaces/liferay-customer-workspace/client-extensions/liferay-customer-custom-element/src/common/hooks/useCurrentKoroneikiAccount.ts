/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useGetKoroneikiAccountByExternalReferenceCode} from '../services/liferay/graphql/koroneiki-accounts/queries/useGetKoroneikiAccountByExternalReferenceCode';
import useAccountKey from './useAccountKey';

export default function useCurrentKoroneikiAccount() {
	const externalReferenceCode = useAccountKey();

	return useGetKoroneikiAccountByExternalReferenceCode(
		externalReferenceCode,
		{
			notifyOnNetworkStatusChange: false,
			skip: !externalReferenceCode,
		}
	);
}
