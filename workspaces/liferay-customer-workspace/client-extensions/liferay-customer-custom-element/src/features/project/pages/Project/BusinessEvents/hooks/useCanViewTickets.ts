/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import {Liferay} from '~/services/liferay';

const useCanViewTickets = (externalReferenceCode?: string, skip?: boolean) => {
	const [canViewTickets, setCanViewTickets] = useState<boolean | undefined>(
		undefined
	);
	const [loading, setLoading] = useState<boolean>(false);

	const fetchAccountObjectKey = useCallback(async () => {
		if (skip || !externalReferenceCode) {
			return;
		}

		setLoading(true);

		try {
			const data = await Liferay.OAuth2Client.FromUserAgentApplication(
				'liferay-customer-etc-spring-boot-oaua'
			)
				.fetch(`/accounts/${externalReferenceCode}/jira/object-key`)
				.then((response: Response) => response.text());

			setCanViewTickets(Boolean(data));
		}
		catch (error) {
			console.error('Error fetching Jira account:', error);
			setCanViewTickets(undefined);
		}
		finally {
			setLoading(false);
		}
	}, [externalReferenceCode, skip]);

	useEffect(() => {
		fetchAccountObjectKey();
	}, [fetchAccountObjectKey]);

	return {canViewTickets, loading};
};

export default useCanViewTickets;
