/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import getCN from 'classnames';
import React from 'react';
import * as API from '~/shared/api';
import Card from '~/shared/components/Card';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import SearchableEntityTable from '~/shared/components/SearchableEntityTable';
import TextTruncate from '~/shared/components/TextTruncate';
import {useQueryPagination} from '~/shared/hooks/useQueryPagination';
import {Sizes} from '~/shared/util/constants';
import {sub} from '~/shared/util/lang';
import {
	NAME,
	createOrderIOMap,
	getDefaultSortOrder,
} from '~/shared/util/pagination';
import {INDIVIDUALS, Routes, toRoute} from '~/shared/util/router';
import {interestListColumns} from '~/shared/util/table-columns';
import URLConstants from '~/shared/util/url-constants';

export const TOTAL_DAYS = 90;

interface IContributionsCellProps {
	className?: string;
	data: {relatedPagesCount: number};
}

export const ContributionsCell = function ContributionsCell({
	className,
	data: {relatedPagesCount},
}: IContributionsCellProps) {
	return (
		<td className={getCN('table-cell-expand', className)}>
			<TextTruncate
				title={sub(Liferay.Language.get('x-contributing-pages'), [
					relatedPagesCount,
				])}
			/>
		</td>
	);
};

interface IInterestsProps {
	channelId: string;
	groupId: string;
	id: string;
}

const Interests: React.FC<IInterestsProps> = ({channelId, groupId, id}) => {
	const {delta, orderIOMap, page, query} = useQueryPagination({
		initialOrderIOMap: createOrderIOMap(NAME, getDefaultSortOrder(NAME)),
	});

	return (
		<Card pageDisplay>
			<SearchableEntityTable
				className="interest-history-table"
				columns={[
					interestListColumns.getName({
						channelId,
						groupId,
						id,
						maxWidth: null,
						routeFn: ({data: {name}}) =>
							name &&
							toRoute(
								Routes.CONTACTS_INDIVIDUAL_INTEREST_DETAILS,
								{
									channelId,
									groupId,
									id,
									interestId: name,
								}
							),
						type: INDIVIDUALS,
					}),
					{
						accessor: 'relatedPagesCount',
						cellRenderer: ContributionsCell,
						label: Liferay.Language.get('contributing-pages'),
						sortable: false,
					},
				]}
				dataSourceFn={API.interests.search}
				dataSourceParams={{
					channelId,
					contactsEntityId: id,
					groupId,
					interestMax: TOTAL_DAYS,
				}}
				delta={delta}
				entityLabel={Liferay.Language.get('interests')}
				noResultsRenderer={() => (
					<NoResultsDisplay
						description={
							<>
								<span className="mr-1">
									{Liferay.Language.get(
										'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
									)}
								</span>

								<ClayLink
									href={
										URLConstants.IndividualsDashboardInterestsDocumentation
									}
									key="DOCUMENTATION"
									target="_blank"
								>
									{Liferay.Language.get(
										'learn-more-about-interests'
									)}
								</ClayLink>
							</>
						}
						icon={{
							border: false,
							size: Sizes.XXXLarge,
							symbol: 'ac_satellite',
						}}
						title={Liferay.Language.get(
							'there-are-no-interests-found'
						)}
					/>
				)}
				orderByOptions={[
					{
						label: Liferay.Language.get('interest'),
						value: NAME,
					},
				]}
				orderIOMap={orderIOMap}
				page={page}
				query={query}
				rowIdentifier="name"
			/>
		</Card>
	);
};

export default Interests;
