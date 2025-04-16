/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {LIST_TYPES} from '~/features/project/utils/constants';
import SearchBuilder from '~/lib/SearchBuilder';
import {useGetListTypeDefinitions} from '~/services/liferay/graphql/list-type-definitions';
import {IOption} from '~/utils/types';

const listTypeUTCTimeZones = LIST_TYPES.utcTimeZones;

export default function useGetUTCTimeZonesList(): {
	loading: boolean;
	utcTimeZonesList: IOption[];
} {
	const {data, loading} = useGetListTypeDefinitions({
		filter: SearchBuilder.eq('name', listTypeUTCTimeZones),
	});

	const utcTimeZonesList = useMemo(
		() =>
			(
				(data?.listTypeDefinitions?.items[0].listTypeEntries ?? []) as {
					key: string;
					name: string;
				}[]
			).map(({key, name}) => ({label: name, value: key})),
		[data?.listTypeDefinitions?.items]
	);

	return {loading, utcTimeZonesList};
}
