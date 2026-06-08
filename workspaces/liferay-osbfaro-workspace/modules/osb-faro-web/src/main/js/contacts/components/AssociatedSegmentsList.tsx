/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {OrderedMap} from 'immutable';
import React from 'react';
import Card from '~/shared/components/Card';
import SearchableEntityTable from '~/shared/components/SearchableEntityTable';
import {useLDPEnabled} from '~/shared/hooks/useLDPEnabled';
import {getPluralMessage} from '~/shared/util/lang';
import {DATE_CREATED, NAME} from '~/shared/util/pagination';
import {OrderParams} from '~/shared/util/records';
import {segmentsListColumns} from '~/shared/util/table-columns';

interface IAssociatedSegmentsListProps {
	channelId: string;
	className?: string;
	dataSourceFn: (params: {[key: string]: any}) => void;
	delta: number;
	groupId: string;
	id: string;
	noResultsRenderer: () => React.ReactElement;
	orderIOMap: OrderedMap<string, OrderParams>;
	page: number;
	query: string;
	timeZoneId: string;
	total: number;
}

const AssociatedSegmentsList: React.FC<IAssociatedSegmentsListProps> = ({
	channelId,
	className,
	dataSourceFn,
	delta,
	groupId,
	id,
	noResultsRenderer,
	orderIOMap,
	page,
	query,
	timeZoneId,
	total,
}) => {
	const LDPEnabled = useLDPEnabled({groupId});

	return (
		<Card
			className={getCN('associated-segments-list-root', className)}
			pageDisplay
		>
			<Card.Header className="align-items-start d-flex justify-content-between">
				<div>
					<Card.Title>
						{Liferay.Language.get('associated-segments')}
					</Card.Title>

					<span className="secondary-info">
						{Liferay.Language.get(
							'list-all-the-segments-that-the-customer-is-currently-associated-with-in-the-last-30-days'
						)}
					</span>

					<div className="secondary-info">
						{getPluralMessage(
							Liferay.Language.get('x-segment'),
							Liferay.Language.get('x-segments'),
							total,
							false,
							[
								<b key="SEGMENT_TOTAL">
									{total.toLocaleString()}
								</b>,
							]
						)}
					</div>
				</div>

				<div className="text-right">
					<span className="text-secondary text-uppercase">
						<small>
							<strong>
								{Liferay.Language.get('last-30-days')}
							</strong>
						</small>
					</span>
				</div>
			</Card.Header>

			<SearchableEntityTable
				columns={[
					segmentsListColumns.getName({channelId, groupId}),
					segmentsListColumns.getSegmentType(LDPEnabled),
					segmentsListColumns.individualAddedDate,
					segmentsListColumns.getDateCreated(timeZoneId),
				].filter(Boolean)}
				dataSourceFn={dataSourceFn}
				dataSourceParams={{channelId, groupId, id}}
				delta={delta}
				enableClearSearch
				entityLabel={Liferay.Language.get('associated-segments')}
				noResultsRenderer={noResultsRenderer}
				orderByOptions={[
					{
						label: Liferay.Language.get('name'),
						value: NAME,
					},
					{
						label: Liferay.Language.get('date-created'),
						value: DATE_CREATED,
					},
				]}
				orderIOMap={orderIOMap}
				page={page}
				query={query}
				rowIdentifier="id"
			/>
		</Card>
	);
};

export default AssociatedSegmentsList;
