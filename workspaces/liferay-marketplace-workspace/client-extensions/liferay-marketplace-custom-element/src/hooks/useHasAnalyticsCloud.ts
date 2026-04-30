/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import HeadlessSalesforceProject from '../services/rest/HeadlessSalesforceProject';

const useHasAnalyticsCloud = (accountKey: string | undefined | null) => {
	const koroneikiAccountKey =
		accountKey && accountKey.startsWith('KOR-') ? accountKey : null;

	const {data, isLoading} = useSWR(
		koroneikiAccountKey
			? `/account/${koroneikiAccountKey}/has-analytics-cloud`
			: null,
		() =>

			// PLACEHOLDER: Remove the need to call getAccountSalesforceProjects to determine if the account has Analytics Cloud. This is a temporary solution until we have a dedicated endpoint for this.

			HeadlessSalesforceProject.getAccountSalesforceProjects(
				koroneikiAccountKey as string
			)
	);

	return {
		hasAnalyticsCloud: Boolean(data?.length),
		isLoading: Boolean(koroneikiAccountKey) && isLoading,
	};
};

export default useHasAnalyticsCloud;
