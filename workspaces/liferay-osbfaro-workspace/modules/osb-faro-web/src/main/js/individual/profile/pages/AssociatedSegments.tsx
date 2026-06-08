/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import React, {useState} from 'react';
import {ConnectedProps, connect} from 'react-redux';
import AssociatedSegmentsList from '~/contacts/components/AssociatedSegmentsList';
import * as API from '~/shared/api';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import {useQueryPagination} from '~/shared/hooks/useQueryPagination';
import {RootState} from '~/shared/store';
import {EntityTypes, Sizes} from '~/shared/util/constants';
import {
	NAME,
	createOrderIOMap,
	getDefaultSortOrder,
} from '~/shared/util/pagination';
import {Routes, SEGMENTS, toRoute} from '~/shared/util/router';
import URLConstants from '~/shared/util/url-constants';

interface IFetchAssociatedSegmentsArgs {
	delta: number;
	groupId: string;
	id: string;
	orderIOMap?: unknown;
	page: number;
	query?: string;
	[key: string]: unknown;
}

const fetchAssociatedSegments = ({
	delta,
	groupId,
	id,
	orderIOMap,
	page,
	query,
	...otherParams
}: IFetchAssociatedSegmentsArgs) =>
	API.individualSegment.search({
		contactsEntityId: id,
		contactsEntityType: EntityTypes.Individual,
		delta,
		groupId,
		orderIOMap,
		page,
		query,
		...otherParams,
	});

const connector = connect((store: RootState, {groupId}: {groupId: string}) => ({
	timeZoneId: store.getIn([
		'projects',
		groupId,
		'data',
		'timeZone',
		'timeZoneId',
	]),
}));

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IAssociatedSegmentsProps extends PropsFromRedux {
	channelId: string;
	groupId: string;
	id: string;
	timeZoneId: string;
}

const AssociatedSegments: React.FC<IAssociatedSegmentsProps> = ({
	channelId,
	groupId,
	id,
	timeZoneId,
}) => {
	const {delta, orderIOMap, page, query} = useQueryPagination({
		initialOrderIOMap: createOrderIOMap(NAME, getDefaultSortOrder(NAME)),
	});

	const [total, setTotal] = useState<number>(0);

	const segmentsDataSourceFn = (dataSourceParams: {[key: string]: any}) =>
		fetchAssociatedSegments({
			channelId,
			groupId,
			id,
			...dataSourceParams,
		} as unknown as IFetchAssociatedSegmentsArgs).then(
			(response: {total: number}) => {
				setTotal(response.total);

				return response;
			}
		);

	const renderNoResults = () => (
		<NoResultsDisplay
			className="m-5"
			description={
				<>
					{Liferay.Language.get(
						'this-individual-does-not-belong-to-a-segment-create-one-to-get-started'
					)}
					<ClayLink
						className="d-block"
						href={URLConstants.CreateSegments}
						key="DOCUMENTATION"
						target="_blank"
					>
						{Liferay.Language.get('learn-more-about-segments')}
						<ClayIcon
							aria-label={Liferay.Language.get(
								'learn-more-about-data-sources'
							)}
							className="ml-1"
							fontSize={8}
							symbol="shortcut"
						/>
					</ClayLink>
				</>
			}
			icon={{
				border: false,
				size: Sizes.XXXLarge,
				symbol: 'ac_satellite',
			}}
			title={Liferay.Language.get('there-are-no-segments-found')}
		>
			<ClayLink
				button
				className="button-root"
				displayType="primary"
				href={toRoute(Routes.CONTACTS_LIST_SEGMENT, {
					channelId,
					groupId,
					type: SEGMENTS,
				})}
			>
				{Liferay.Language.get('create-segment')}
			</ClayLink>
		</NoResultsDisplay>
	);

	return (
		<AssociatedSegmentsList
			channelId={channelId}
			dataSourceFn={segmentsDataSourceFn}
			delta={delta}
			groupId={groupId}
			id={id}
			noResultsRenderer={renderNoResults}
			orderIOMap={orderIOMap}
			page={page}
			query={query}
			timeZoneId={timeZoneId}
			total={total}
		/>
	);
};

export default connector(AssociatedSegments);
