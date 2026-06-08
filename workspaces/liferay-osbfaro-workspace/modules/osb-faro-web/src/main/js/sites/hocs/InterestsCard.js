/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {graphql} from '@apollo/client/react/hoc';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import React from 'react';
import {useParams} from 'react-router-dom';
import Card from '~/shared/components/Card';
import BaseCard from '~/shared/components/base-card';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import {withTableData} from '~/shared/hoc';
import InterestsQuery from '~/shared/queries/InterestsQuery';
import {CompositionTypes, RangeKeyTimeRanges} from '~/shared/util/constants';
import {Routes, setUriQueryValues, toRoute} from '~/shared/util/router';
import {compositionListColumns} from '~/shared/util/table-columns';
import URLConstants from '~/shared/util/url-constants';

import {
	getMapResultToProps,
	mapCardPropsToOptions,
} from './mappers/composition-query';

const withData = () =>
	graphql(InterestsQuery, {
		options: mapCardPropsToOptions,
		props: getMapResultToProps(CompositionTypes.SiteInterests),
	});

const TableWithData = withTableData(withData, {
	emptyDescription: (
		<>
			<span className="mr-1">
				{Liferay.Language.get(
					'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
				)}
			</span>

			<a
				href={URLConstants.SitesDashboardSearchTermsAndInterests}
				key="DOCUMENTATION"
				target="_blank"
			>
				{Liferay.Language.get('learn-more-about-interests')}
			</a>
		</>
	),
	emptyTitle: Liferay.Language.get(
		'there-are-no-interests-on-the-selected-period'
	),
	getColumns: ({maxCount, totalCount}) => [
		compositionListColumns.getRelativeMetricBar({
			label: `${Liferay.Language.get(
				'interest-topics'
			)} | ${Liferay.Language.get('sessions')}`,
			maxCount,
			showName: true,
			totalCount,
		}),
		compositionListColumns.getPercentOf({
			metricName: Liferay.Language.get('sessions'),
			totalCount,
		}),
	],
	rowIdentifier: 'name',
});

const InterestsCard = () => {
	const {channelId, groupId} = useParams();

	const {Last7Days, Last30Days, Last90Days, Yesterday} = RangeKeyTimeRanges;

	const rangeKeys = [Yesterday, Last7Days, Last30Days, Last90Days];

	return (
		<BaseCard
			className="interests-card-root"
			label={Liferay.Language.get('interests')}
			legacyDropdownRangeKey={false}
			rangeKeys={rangeKeys}
			reportContainer={ReportContainer.InterestsCard}
		>
			{({rangeSelectors}) => (
				<>
					<TableWithData
						channelId={channelId}
						rangeSelectors={rangeSelectors}
						rowBordered={false}
					/>

					<Card.Footer>
						<ClayLink
							borderless
							button
							className="button-root"
							displayType="secondary"
							href={setUriQueryValues(
								rangeSelectors,
								toRoute(Routes.SITES_INTERESTS, {
									channelId,
									groupId,
								})
							)}
							small
						>
							{Liferay.Language.get('all-interests')}

							<ClayIcon
								className="icon-root ml-2"
								symbol="angle-right-small"
							/>
						</ClayLink>
					</Card.Footer>
				</>
			)}
		</BaseCard>
	);
};

export default InterestsCard;
