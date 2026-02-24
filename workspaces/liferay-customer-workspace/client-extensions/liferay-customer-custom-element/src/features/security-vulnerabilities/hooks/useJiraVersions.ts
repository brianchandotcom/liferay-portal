/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as OAuth2 from '@liferay/oauth2-provider-web/client';
import {useCallback, useEffect, useState} from 'react';

const useJiraVersions = () => {
	const [jiraVersions, setJiraVersions] = useState<string[] | undefined>(
		undefined
	);
	const [loading, setLoading] = useState(true);

	const fetchJiraVersions = useCallback(async () => {
		setLoading(true);

		try {
			const oauth2Client = await OAuth2.FromUserAgentApplication(
				'liferay-customer-etc-spring-boot-oaua'
			);

			const response: string[] = await oauth2Client
				.fetch('/jira/security-vulnerabilities/affected-versions')
				.then((response: {json: () => any}) => response.json());

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
