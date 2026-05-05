/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import useAccountKey from '~/hooks/useAccountKey';
import {getBusinessEventById} from '~/services/liferay/rest/jira/Jira';
import {IBusinessEvent} from '~/utils/types';

export default function useGetBusinessEvent(id: string): {
	businessEvent: IBusinessEvent | undefined;
	fetchBusinessEvent: () => Promise<void>;
	loading: boolean;
} {
	const [businessEvent, setBusinessEvent] = useState<
		IBusinessEvent | undefined
	>(undefined);

	const [loading, setLoading] = useState(true);

	const accountKey = useAccountKey();
	const fetchBusinessEvent = useCallback(async () => {
		if (!accountKey) {
			return;
		}

		setLoading(true);

		try {
			const businessEventResponse = await getBusinessEventById(
				accountKey,
				id
			);

			setBusinessEvent(businessEventResponse as IBusinessEvent);
		}
		catch (error) {
			console.error('Unable to fetch business event:', error);
		}
		finally {
			setLoading(false);
		}
	}, [accountKey, id]);

	useEffect(() => {
		if (!id) {
			setLoading(true);

			return;
		}

		fetchBusinessEvent();
	}, [fetchBusinessEvent, id]);

	return {businessEvent, fetchBusinessEvent, loading};
}
