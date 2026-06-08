/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useQuery} from '@apollo/client';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import React from 'react';
import {useParams} from 'react-router-dom';
import Card from '~/shared/components/Card';
import ErrorDisplay from '~/shared/components/ErrorDisplay';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import StatesRenderer from '~/shared/components/states-renderer/StatesRenderer';
import Table, {Column} from '~/shared/components/table';
import IndividualInterestsQuery, {
	IIndividualInterestsData,
	IIndividualInterestsVariables,
} from '~/shared/queries/IndividualInterestsQuery';
import {OrderByDirections} from '~/shared/util/constants';
import {COUNT} from '~/shared/util/pagination';
import {Routes, toRoute} from '~/shared/util/router';
import {compositionListColumns} from '~/shared/util/table-columns';
import URLConstants from '~/shared/util/url-constants';

const InterestsCard: React.FC<React.HTMLAttributes<HTMLElement>> = () => {
	const {channelId = '', groupId = ''} = useParams<{
		channelId: string;
		groupId: string;
	}>();
	const {
		data = {
			individualInterests: {compositions: [], maxCount: 0, totalCount: 0},
		},
		error,
		loading,
	} = useQuery<IIndividualInterestsData, IIndividualInterestsVariables>(
		IndividualInterestsQuery,
		{
			variables: {
				active: true,
				channelId,
				id: groupId,
				size: 5,
				sort: {
					column: COUNT,
					type: OrderByDirections.Descending,
				},
				start: 0,
			},
		}
	);

	const {
		individualInterests: {compositions: items, maxCount, totalCount},
	} = data;

	const columns = [
		compositionListColumns.getName({
			label: Liferay.Language.get('topic'),
			maxWidth: 200,
			routeFn: ({data: {name}}: {data: {name: string}}) =>
				name &&
				toRoute(Routes.CONTACTS_INDIVIDUALS_INTEREST_DETAILS, {
					channelId,
					groupId,
					interestId: name,
				}),
			sortable: false,
		}),
		compositionListColumns.getRelativeMetricBar({
			label: Liferay.Language.get('total-individuals'),
			maxCount,
			totalCount,
		}),
		compositionListColumns.getPercentOf({
			metricName: Liferay.Language.get('total-individuals'),
			totalCount,
		}),
	] as Column[];

	return (
		<Card
			className="interests-card-root"
			minHeight={536}
			reportContainer={ReportContainer.TopInterestsAsOfYesterdayCard}
		>
			<Card.Header>
				<Card.Title>
					{Liferay.Language.get('top-interests-as-of-yesterday')}
				</Card.Title>
			</Card.Header>

			<StatesRenderer
				empty={!items.length}
				error={!!error}
				loading={loading}
			>
				<StatesRenderer.Empty
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
					showIcon={false}
					title={Liferay.Language.get('there-are-no-interests-found')}
				/>

				<StatesRenderer.Error>
					<ErrorDisplay spacer />
				</StatesRenderer.Error>

				<StatesRenderer.Success>
					<Table
						columns={columns}
						items={items}
						rowIdentifier="name"
					/>
				</StatesRenderer.Success>
			</StatesRenderer>

			<Card.Footer>
				<ClayLink
					borderless
					button
					className="button-root"
					displayType="secondary"
					href={toRoute(Routes.CONTACTS_INDIVIDUALS_INTERESTS, {
						channelId,
						groupId,
					})}
					small
				>
					{Liferay.Language.get('view-all-interests')}

					<ClayIcon
						className="icon-root ml-2"
						symbol="angle-right-small"
					/>
				</ClayLink>
			</Card.Footer>
		</Card>
	);
};

export default InterestsCard;
