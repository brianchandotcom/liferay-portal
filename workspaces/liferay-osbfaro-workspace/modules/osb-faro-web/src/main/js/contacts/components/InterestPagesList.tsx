/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import * as API from '~/shared/api';
import SearchableEntityTable from '~/shared/components/SearchableEntityTable';
import {useQueryPagination} from '~/shared/hooks/useQueryPagination';
import {
	TITLE,
	UNIQUE_VISITS_COUNT,
	URL,
	createOrderIOMap,
} from '~/shared/util/pagination';
import {Routes} from '~/shared/util/router';
import {pagesListColumns} from '~/shared/util/table-columns';

const PAGES_ORDER_BY_OPTIONS = [
	{
		label: Liferay.Language.get('page-title'),
		value: TITLE,
	},
	{
		label: Liferay.Language.get('url'),
		value: URL,
	},
];

interface IInterestPages {
	channelId?: string;
	dataSourceFn: () => void;
	dataSourceParams: any;
	entityLabel: string;
	groupId?: string;
	rowIdentifier: string[];
}

const ActivePagesList: React.FC<IInterestPages> = ({
	channelId,
	groupId,
	...otherProps
}) => {
	const {delta, orderIOMap, page, query} = useQueryPagination({
		initialOrderIOMap: createOrderIOMap(UNIQUE_VISITS_COUNT),
	});

	return (
		<SearchableEntityTable
			{...otherProps}
			columns={[
				pagesListColumns.getTitleUrl({
					channelId,
					groupId,
					route: Routes.SITES_TOUCHPOINTS_OVERVIEW,
				}),
				pagesListColumns.url,
				pagesListColumns.viewCount,
			]}
			delta={delta}
			orderByOptions={[
				...PAGES_ORDER_BY_OPTIONS,
				{
					label: Liferay.Language.get('views'),
					value: UNIQUE_VISITS_COUNT,
				},
			]}
			orderIOMap={orderIOMap}
			page={page}
			query={query}
		/>
	);
};
const InactivePagesList: React.FC<IInterestPages> = ({
	channelId,
	groupId,
	...otherProps
}) => {
	const {delta, orderIOMap, page, query} = useQueryPagination({
		initialOrderIOMap: createOrderIOMap(URL),
	});

	return (
		<SearchableEntityTable
			{...otherProps}
			columns={[
				pagesListColumns.getTitleUrl({
					channelId,
					groupId,
					route: Routes.SITES_TOUCHPOINTS_OVERVIEW,
				}),
				pagesListColumns.url,
				pagesListColumns.inactiveViewCount,
			]}
			delta={delta}
			orderByOptions={PAGES_ORDER_BY_OPTIONS}
			orderIOMap={orderIOMap}
			page={page}
			query={query}
		/>
	);
};

const InterestPagesList = ({
	dataSourceParams,
	...otherProps
}: {
	dataSourceParams: {[key: string]: any};
	[key: string]: any;
}) => {
	const {active} = dataSourceParams;

	const PagesListComponent = active ? ActivePagesList : InactivePagesList;

	return (
		<PagesListComponent
			{...otherProps}
			dataSourceFn={API.pagesVisited.search}
			dataSourceParams={dataSourceParams}
			entityLabel={Liferay.Language.get('pages')}
			rowIdentifier={['url', 'dataSourceId']}
		/>
	);
};

export default InterestPagesList;
