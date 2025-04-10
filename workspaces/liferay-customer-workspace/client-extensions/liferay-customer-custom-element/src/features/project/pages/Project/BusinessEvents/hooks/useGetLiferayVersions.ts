/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {LIST_TYPES} from '~/features/project/utils/constants';
import SearchBuilder from '~/lib/SearchBuilder';
import {useGetListTypeDefinitions} from '~/services/liferay/graphql/list-type-definitions';
import {IOption} from '~/utils/types';

export default function useGetLiferayVersions(): {
	dxpMajorVersions: IOption[];
	dxpMinorVersions: IOption[];
	dxpMinorVersionsAndPortalMajorVersions: IOption[];
	loading: boolean;
	portalMajorVersions: IOption[];
} {
	const {data: dxpMajorVersionsData, loading: loadingDXPMajorVersions} =
		useGetListTypeDefinitions({
			filter: SearchBuilder.eq('name', LIST_TYPES.dxpMajorVersion),
		});

	const dxpMajorVersions = useMemo(
		() =>
			(
				(dxpMajorVersionsData?.listTypeDefinitions?.items[0]
					.listTypeEntries ?? []) as {
					key: string;
					name: string;
				}[]
			).map(({key, name}) => ({label: name, value: key})),
		[dxpMajorVersionsData?.listTypeDefinitions?.items]
	);

	const {data: dxpMinorVersionsData, loading: loadingDXPMinorVersions} =
		useGetListTypeDefinitions({
			filter: SearchBuilder.eq('name', LIST_TYPES.dxpMinorVersion),
		});

	const dxpMinorVersions = useMemo(
		() =>
			(
				(dxpMinorVersionsData?.listTypeDefinitions?.items[0]
					.listTypeEntries ?? []) as {
					key: string;
					name: string;
				}[]
			).map(({key, name}) => ({label: name, value: key})),
		[dxpMinorVersionsData?.listTypeDefinitions?.items]
	);

	const {
		data: dxpMinorVersionsAndPortalMajorVersionsData,
		loading: loadingDXPMinorVersionsAndPortalMajorVersions,
	} = useGetListTypeDefinitions({
		filter: SearchBuilder.eq('name', LIST_TYPES.dxpMinorVersion),
	});

	const dxpMinorVersionsAndPortalMajorVersions = useMemo(
		() =>
			(
				(dxpMinorVersionsAndPortalMajorVersionsData?.listTypeDefinitions
					?.items[0].listTypeEntries ?? []) as {
					key: string;
					name: string;
				}[]
			).map(({key, name}) => ({label: name, value: key})),
		[dxpMinorVersionsAndPortalMajorVersionsData?.listTypeDefinitions?.items]
	);

	const {data: portalMajorVersionsData, loading: loadingPortalMajorVersions} =
		useGetListTypeDefinitions({
			filter: SearchBuilder.eq('name', LIST_TYPES.portalMajorVersion),
		});

	const portalMajorVersions = useMemo(
		() =>
			(
				(portalMajorVersionsData?.listTypeDefinitions?.items[0]
					.listTypeEntries ?? []) as {
					key: string;
					name: string;
				}[]
			).map(({key, name}) => ({label: name, value: key})),
		[portalMajorVersionsData?.listTypeDefinitions?.items]
	);

	const loading = useMemo(
		() =>
			loadingDXPMajorVersions ||
			loadingDXPMinorVersions ||
			loadingDXPMinorVersionsAndPortalMajorVersions ||
			loadingPortalMajorVersions,
		[
			loadingDXPMajorVersions,
			loadingDXPMinorVersions,
			loadingDXPMinorVersionsAndPortalMajorVersions,
			loadingPortalMajorVersions,
		]
	);

	return {
		dxpMajorVersions,
		dxpMinorVersions,
		dxpMinorVersionsAndPortalMajorVersions,
		loading,
		portalMajorVersions,
	};
}
