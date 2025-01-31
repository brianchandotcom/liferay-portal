/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import {Liferay} from '~/services/liferay';

const useJiraVersions = () => {
	const [jiraVersions, setJiraVersions] = useState<string[] | undefined>(
		undefined
	);
	const [loading, setLoading] = useState(true);

	const fetchJiraVersions = useCallback(async () => {
		setLoading(true);

		try {
			const response: string[] =
				await Liferay.OAuth2Client.FromUserAgentApplication(
					'liferay-customer-etc-spring-boot-oaua'
				)
					.fetch('/jira/security-vulnerabilities/affected-versions')
					.then((response) => response.json());

			setJiraVersions(response);
		}
		catch (error) {
			console.error('Error fetching Jira data:', error);

			setJiraVersions(undefined);
		}
		finally {
			setLoading(false);
		}
	}, []);

	useEffect(() => {
		fetchJiraVersions();
	}, [fetchJiraVersions]);

	return {jiraVersions, loading};
};

export default useJiraVersions;
