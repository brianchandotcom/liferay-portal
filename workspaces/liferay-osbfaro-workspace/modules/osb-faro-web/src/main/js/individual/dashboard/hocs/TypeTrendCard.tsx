/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OperationOption, graphql} from '@apollo/client/react/hoc';
import React from 'react';
import {useParams} from 'react-router-dom';
import {compose} from 'redux';
import Card from '~/shared/components/Card';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import {withError, withLoading} from '~/shared/hoc';
import IndividualMetricsQuery from '~/shared/queries/IndividualMetricsQuery';
import {RangeKeyTimeRanges} from '~/shared/util/constants';
import {INTERVAL_KEY_MAP} from '~/shared/util/time';

import TypeTrend from '../components/TypeTrend';
import {
	mapPropsToOptions,
	mapResultToProps,
} from '../hocs/mappers/individual-metrics-query';

const TypeTrendWithData = compose<any>(
	graphql(IndividualMetricsQuery, {
		options: mapPropsToOptions,
		props: mapResultToProps,
	} as OperationOption<object, object>),
	withLoading(),
	withError({page: false})
)(TypeTrend);

const TypeTrendCard: React.FC<React.HTMLAttributes<HTMLElement>> = () => {
	const {channelId} = useParams();

	return (
		<Card
			className="text-secondary type-trend-card-root"
			reportContainer={ReportContainer.CurrentTotalsCard}
		>
			<Card.Body>
				<TypeTrendWithData
					channelId={channelId}
					interval={INTERVAL_KEY_MAP.week}
					rangeSelectors={{rangeKey: RangeKeyTimeRanges.Last30Days}}
				/>
			</Card.Body>
		</Card>
	);
};

export default TypeTrendCard;
