/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DocumentNode, useQuery} from '@apollo/client';
import React from 'react';
import {useParams} from 'react-router-dom';
import HTMLBarChart from '~/shared/components/HTMLBarChart';
import InfoPopover, {IInfoPopoverProps} from '~/shared/components/InfoPopover';
import {RangeSelectors} from '~/shared/types';
import {RawFilters, getFilters} from '~/shared/util/filter';
import {fetchPolicyDefinition} from '~/shared/util/graphql';
import {
	getSafeDecodedURIComponent,
	getSafeRangeSelectors,
	getSafeTouchpoint,
} from '~/shared/util/util';

import AudienceReportDonut from './AudienceReportDonut';
import AudienceReportStateRenderer from './AudienceReportStateRenderer';
import {IAudienceReportBaseCardProps, Name, TData} from './types';
import {formatData} from './util';

const AudienceReportTitle: React.FC<IInfoPopoverProps> = ({content, title}) => (
	<div className="d-inline-flex gap">
		<div className="h4 mb-3 mr-2 text-center text-secondary title">
			{title}
		</div>

		{content && <InfoPopover content={content} title={title} />}
	</div>
);

interface IAudienceReportWithDataProps<TRawData>
	extends Partial<IAudienceReportBaseCardProps> {
	data: TRawData;
	mapper: (data: TRawData) => TData;
	name: Name;
}

function AudienceReportWithData<TRawData>({
	data,
	knownIndividualsTitle,
	mapper,
	name,
	segmentsTitle = Liferay.Language.get('viewer-segments'),
	uniqueVisitorsTitle = Liferay.Language.get('visitors'),
}: IAudienceReportWithDataProps<TRawData>) {
	const result: TData = mapper(data);

	const {knownIndividuals, segments, uniqueVisitors} = formatData(result);

	return (
		<div className="audience-report-chart row w-100">
			<div className="col-sm-6">
				<div className="row">
					<div className="col-sm-6 text-center">
						<AudienceReportTitle title={uniqueVisitorsTitle} />

						<AudienceReportDonut {...uniqueVisitors} />
					</div>

					<div className="col-sm-6 text-center">
						<AudienceReportTitle
							content={
								name === Name.Page
									? Liferay.Language.get(
											'only-known-individuals-that-interacted-with-the-current-page-are-accounted-for-in-this-chart'
										)
									: Liferay.Language.get(
											'only-known-individuals-that-interacted-with-the-current-asset-are-accounted-for-in-this-chart'
										)
							}
							title={knownIndividualsTitle}
						/>

						<AudienceReportDonut {...knownIndividuals} />
					</div>
				</div>
			</div>

			<div className="col-sm-6 pl-5">
				<AudienceReportTitle
					content={
						name === Name.Page
							? Liferay.Language.get(
									'only-segmented-known-individuals-that-interacted-with-the-current-page-are-accounted-for-in-this-chart'
								)
							: Liferay.Language.get(
									'only-segmented-known-individuals-that-interacted-with-the-current-asset-are-accounted-for-in-this-chart'
								)
					}
					title={segmentsTitle}
				/>

				<div className="audience-report-chart-bar">
					<HTMLBarChart {...segments} />
				</div>
			</div>
		</div>
	);
}

interface IAudienceReportProps<TRawData>
	extends Partial<IAudienceReportBaseCardProps> {
	Query: DocumentNode;
	experienceId?: string | null;
	filters: RawFilters;
	mapper: (data: TRawData) => TData;
	name: Name;
	rangeSelectors: RangeSelectors;
}

function AudienceReport<TRawData>({
	Query,
	experienceId,
	filters,
	rangeSelectors,
	...otherProps
}: IAudienceReportProps<TRawData>) {
	const {assetId, channelId, title, touchpoint} = useParams();
	const {data, error, loading} = useQuery(Query, {
		fetchPolicy: fetchPolicyDefinition(rangeSelectors),
		variables: {
			assetId,
			touchpoint: getSafeTouchpoint(touchpoint as string),
			...(experienceId && {experienceId}),
			...(otherProps.name !== Name.ObjectEntry && {
				channelId,
				title: getSafeDecodedURIComponent(title as string),
			}),
			...getFilters(filters),
			...getSafeRangeSelectors(rangeSelectors),
		},
	});

	return (
		<AudienceReportStateRenderer error={error!} loading={loading}>
			<AudienceReportWithData {...otherProps} data={data} />
		</AudienceReportStateRenderer>
	);
}

export default AudienceReport;
