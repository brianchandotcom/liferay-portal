/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {JSM_FIELDS} from '~/features/project/utils/constants';
import {getFieldOptions} from '~/services/liferay/rest/jira/Jira';
import {IOption} from '~/utils/types';

export default function useGetBusinessEventTypesList(): {
	businessEventTypesList: IOption[];
	error: boolean;
	loading: boolean;
} {
	const [businessEventTypesList, setBusinessEventTypesList] = useState<
		IOption[]
	>([]);
	const [error, setError] = useState(false);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const fetchListTypeEntries = async () => {
			try {
				const response = await getFieldOptions(JSM_FIELDS.eventType);

				setBusinessEventTypesList(
					response
						.map((entry: any) => ({
							label: entry.name,
							value: entry.key,
						}))
						.sort((a: any, b: any) =>
							a.label.localeCompare(b.label)
						)
				);
			}
			catch (error) {
				console.error('Error fetching business event types:', error);

				setError(true);
			}
			finally {
				setLoading(false);
			}
		};

		fetchListTypeEntries();
	}, []);

	return {businessEventTypesList, error, loading};
}
