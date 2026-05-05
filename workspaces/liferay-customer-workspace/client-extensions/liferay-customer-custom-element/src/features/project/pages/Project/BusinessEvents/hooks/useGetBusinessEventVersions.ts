/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import useAccountKey from '~/hooks/useAccountKey';
import {getBusinessEventVersions} from '~/services/liferay/rest/jira/Jira';
import {IBusinessEventVersion} from '~/utils/types';

export default function useGetBusinessEventVersions(id: string): {
	businessEventVersions: IBusinessEventVersion[];
	fetchBusinessEventVersions: () => Promise<void>;
	loading: boolean;
} {
	const [businessEventVersions, setBusinessEventVersions] = useState<
		IBusinessEventVersion[]
	>([]);

	const [loading, setLoading] = useState(true);

	const accountKey = useAccountKey();
	const fetchBusinessEventVersions = useCallback(async () => {
		if (!accountKey || !id) {
			return;
		}

		setLoading(true);

		try {
			const response = await getBusinessEventVersions(accountKey, id);

			setBusinessEventVersions(
				(response.items || []) as IBusinessEventVersion[]
			);
		}
		catch (error) {
			console.error('Unable to fetch business event versions:', error);
		}
		finally {
			setLoading(false);
		}
	}, [accountKey, id]);

	useEffect(() => {
		fetchBusinessEventVersions();
	}, [fetchBusinessEventVersions]);

	return {businessEventVersions, fetchBusinessEventVersions, loading};
}
