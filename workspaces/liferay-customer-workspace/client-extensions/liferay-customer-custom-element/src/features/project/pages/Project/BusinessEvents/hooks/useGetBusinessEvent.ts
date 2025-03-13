/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import {getBusinessEventById} from '~/services/liferay/api';
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

	const fetchBusinessEvent = useCallback(async () => {
		setLoading(true);

		try {
			const data = await getBusinessEventById(id);

			setBusinessEvent(data as unknown as IBusinessEvent);
		}
		catch (error) {
			console.error('Error fetching business event:', error);
		}
		finally {
			setLoading(false);
		}
	}, [id]);

	useEffect(() => {
		if (!id) {
			setLoading(true);

			return;
		}

		fetchBusinessEvent();
	}, [fetchBusinessEvent, id]);

	return {businessEvent, fetchBusinessEvent, loading};
}
