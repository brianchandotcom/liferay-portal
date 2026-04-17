/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {JSM_FIELDS, LIST_TYPES} from '~/features/project/utils/constants';
import {getListTypeEntriesLegacy} from '~/services/liferay/api';
import {getFieldOptions} from '~/services/liferay/rest/jira/Jira';
import {IOption} from '~/utils/types';

import useIsJiraBackend from './useIsJiraBackend';

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

	const isJiraBackend = useIsJiraBackend();

	useEffect(() => {
		const fetchListTypeEntries = async () => {
			try {
				const response = isJiraBackend
					? await getFieldOptions(JSM_FIELDS.eventType)
					: await getListTypeEntriesLegacy(
							LIST_TYPES.businessEventTypes
						);

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
	}, [isJiraBackend]);

	return {businessEventTypesList, error, loading};
}
