/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {graphql} from '@apollo/client/react/hoc';
import ClayLink from '@clayui/link';
import React, {useState} from 'react';
import Card from '~/shared/components/Card';
import {
	compose,
	withBaseResults,
	withQueryPagination,
	withQueryRangeSelectors,
} from '~/shared/hoc';
import getMetricsMapper from '~/shared/hoc/mappers/metrics';
import knownIndividualsListAssetQuery from '~/shared/queries/knownIndividualsListAssetQuery';
import {RangeSelectors} from '~/shared/types';
import {Sizes} from '~/shared/util/constants';
import {NAME, VIEWS_METRIC, createOrderIOMap} from '~/shared/util/pagination';
import {Routes} from '~/shared/util/router';
import {metricsListColumns} from '~/shared/util/table-columns';
import URLConstants from '~/shared/util/url-constants';

const withData = () =>
	graphql(
		knownIndividualsListAssetQuery('blog', VIEWS_METRIC),
		getMetricsMapper((result) => ({
			items: result.blog.viewsMetric.individuals.individuals,
			total: result.blog.viewsMetric.individuals.total,
		}))
	);

const TableWithData = withBaseResults(withData, {
	emptyDescription: (
		<>
			<span className="mr-1">
				{Liferay.Language.get(
					'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources,-or-you-can-try-a-different-date-range'
				)}
			</span>

			<ClayLink
				href={URLConstants.IndividualsDashboardDocumentation}
				key="DOCUMENTATION"
				target="_blank"
			>
				{Liferay.Language.get('learn-more-about-individuals')}
			</ClayLink>
		</>
	),
	emptyIcon: {
		border: false,
		size: Sizes.XXXLarge,
		symbol: 'ac_satellite',
	},
	emptyTitle: Liferay.Language.get('there-are-no-individuals-found'),
	getColumns: ({
		router: {
			params: {channelId, groupId},
		},
	}: any) => [
		metricsListColumns.getNameEmail({
			channelId,
			groupId,
			route: Routes.CONTACTS_INDIVIDUAL,
		}),
	],
	legacyDropdownRangeKey: false,
	rowIdentifier: 'id',
});

const KnownIndividualsListCard = ({
	rangeSelectors: initialRangeSelectors,
	...otherProps
}: any) => {
	const [rangeSelectors, setRangeSelectors] = useState<RangeSelectors>(
		initialRangeSelectors
	);

	return (
		<Card className="known-individuals-root" pageDisplay>
			<TableWithData
				{...otherProps}
				onRangeSelectorsChange={setRangeSelectors}
				rangeSelectors={rangeSelectors}
			/>
		</Card>
	);
};

export default compose<React.ComponentType<any>>(
	withQueryPagination({initialOrderIOMap: createOrderIOMap(NAME)}),
	withQueryRangeSelectors()
)(KnownIndividualsListCard);
