/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import useAccountKey from '~/hooks/useAccountKey';
import {getBusinessEventVersionsLegacy} from '~/services/liferay/api';
import {getBusinessEventVersions} from '~/services/liferay/rest/jira/Jira';
import {IBusinessEventVersion} from '~/utils/types';

import useIsJiraBackend from './useIsJiraBackend';

export default function useGetBusinessEventVersions(filterQuery: string): {
	businessEventVersions: IBusinessEventVersion[];
	fetchBusinessEventVersions: () => Promise<void>;
	loading: boolean;
} {
	const [businessEventVersions, setBusinessEventVersions] = useState<
		IBusinessEventVersion[]
	>([]);

	const [loading, setLoading] = useState(true);

	const accountKey = useAccountKey();
	const isJiraBackend = useIsJiraBackend();

	const fetchBusinessEventVersions = useCallback(async () => {
		if (!accountKey) {
			return;
		}

		setLoading(true);

		try {
			const response = isJiraBackend
				? await getBusinessEventVersions(accountKey, filterQuery)
				: await getBusinessEventVersionsLegacy(filterQuery);

			setBusinessEventVersions(
				(response.items || []) as IBusinessEventVersion[]
			);
		}
		catch (error) {
			console.error('Error fetching business event versions:', error);
		}
		finally {
			setLoading(false);
		}
	}, [accountKey, filterQuery, isJiraBackend]);

	useEffect(() => {
		fetchBusinessEventVersions();
	}, [fetchBusinessEventVersions]);

	return {businessEventVersions, fetchBusinessEventVersions, loading};
}
