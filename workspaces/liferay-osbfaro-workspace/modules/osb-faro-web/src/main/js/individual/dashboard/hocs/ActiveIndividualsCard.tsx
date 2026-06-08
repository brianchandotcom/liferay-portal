/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OperationOption, graphql} from '@apollo/client/react/hoc';
import React from 'react';
import {useParams} from 'react-router-dom';
import {compose} from 'redux';
import Card from '~/shared/components/Card';
import BaseCard from '~/shared/components/base-card';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import {withError} from '~/shared/hoc';
import IndividualSiteMetricsQuery from '~/shared/queries/IndividualSiteMetricsQuery';

import ActiveIndividualsChart from '../components/ActiveIndividualsChart';
import {
	mapPropsToOptions,
	mapResultToProps,
} from '../hocs/mappers/site-metrics-query';

const ChartWithData = compose<any>(
	graphql(IndividualSiteMetricsQuery, {
		options: mapPropsToOptions,
		props: mapResultToProps,
	} as OperationOption<object, object>),
	withError({page: false})
)(ActiveIndividualsChart);

const ActiveIndividualsCard = () => {
	const {channelId} = useParams();

	return (
		<BaseCard
			label={Liferay.Language.get('active-individuals')}
			legacyDropdownRangeKey={false}
			minHeight={536}
			reportContainer={ReportContainer.ActiveIndividualsCard}
			showInterval
		>
			{({interval, rangeSelectors}) => (
				<Card.Body className="justify-content-center">
					<ChartWithData
						active
						channelId={channelId}
						interval={interval}
						rangeSelectors={rangeSelectors}
					/>
				</Card.Body>
			)}
		</BaseCard>
	);
};

export default ActiveIndividualsCard;
