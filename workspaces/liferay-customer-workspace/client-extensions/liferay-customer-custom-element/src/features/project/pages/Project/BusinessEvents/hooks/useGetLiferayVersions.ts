/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {JSM_OBJECT_TYPES, LIST_TYPES} from '~/features/project/utils/constants';
import {getListTypeEntriesLegacy} from '~/services/liferay/api';
import {getJSMObjects} from '~/services/liferay/rest/jira/Jira';
import sortLiferayVersions from '~/utils/sortLiferayVersions';
import {IOption} from '~/utils/types';

import useIsJiraBackend from './useIsJiraBackend';

export default function useGetLiferayVersions(): {
	error: boolean;
	loading: boolean;
	productVersions: IOption[];
} {
	const [productVersions, setProductVersions] = useState<IOption[]>([]);
	const [error, setError] = useState(false);
	const [loading, setLoading] = useState(true);

	const isJiraBackend = useIsJiraBackend();

	useEffect(() => {
		const fetchLiferayVersions = async () => {
			try {
				if (isJiraBackend) {
					const response = await getJSMObjects(
						JSM_OBJECT_TYPES.productVersion
					);

					setProductVersions(
						sortLiferayVersions(
							response.map((entry: any) => ({
								key: entry.key,
								name: entry.name,
							}))
						).map(({key, name}) => ({label: name, value: key}))
					);
				}
				else {
					const response = await getListTypeEntriesLegacy(
						LIST_TYPES.dxpMinorVersionAndPortalMajorVersion
					);

					setProductVersions(
						sortLiferayVersions(
							response.map((entry: any) => ({
								key: entry.key,
								name: entry.name,
							}))
						).map(({key, name}) => ({label: name, value: key}))
					);
				}
			}
			catch (error) {
				console.error('Error fetching Liferay versions:', error);

				setError(true);
			}
			finally {
				setLoading(false);
			}
		};

		fetchLiferayVersions();
	}, [isJiraBackend]);

	return {
		error,
		loading,
		productVersions,
	};
}
