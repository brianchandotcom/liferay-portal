/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import {getBusinessEventVersions} from '~/services/liferay/api';
import {IBusinessEventVersion} from '~/utils/types';

export default function useGetBusinessEventVersions(filterQuery: string): {
	businessEventVersions: IBusinessEventVersion[];
	fetchBusinessEventVersions: () => Promise<void>;
	loading: boolean;
} {
	const [businessEventVersions, setBusinessEventVersions] = useState<
		IBusinessEventVersion[]
	>([]);

	const [loading, setLoading] = useState(true);

	const fetchBusinessEventVersions = useCallback(async () => {
		try {
			const businessEventVersionsResponse =
				await getBusinessEventVersions(filterQuery);

			setBusinessEventVersions(businessEventVersionsResponse.items);
		}
		catch (error) {
			console.error('Error fetching business event versions:', error);
		}
		finally {
			setLoading(false);
		}
	}, [filterQuery]);

	useEffect(() => {
		fetchBusinessEventVersions();
	}, [fetchBusinessEventVersions]);

	return {businessEventVersions, fetchBusinessEventVersions, loading};
}
