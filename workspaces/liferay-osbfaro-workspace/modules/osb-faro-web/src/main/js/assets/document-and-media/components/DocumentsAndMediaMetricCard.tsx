/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React from 'react';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import MetricBaseCard, {
	IGenericMetricBaseCardProps,
} from '~/shared/components/metric-card/MetricBaseCard';
import {useAssetVariables} from '~/shared/components/metric-card/hooks';
import {
	CommentsMetric,
	DownloadsMetric,
	ImpressionMadeMetric,
	Metric,
	RatingsMetric,
} from '~/shared/components/metric-card/metrics';
import {
	AssetMetricQuery,
	AssetTabsQuery,
} from '~/shared/components/metric-card/queries';
import {ICommonVariables} from '~/shared/types';
import URLConstants from '~/shared/util/url-constants';

const NAME = 'document';

const DocumentsAndMediaMetricCard: React.FC<IGenericMetricBaseCardProps> = (
	props
) => {
	const variables = (commonVariables: ICommonVariables) =>
		useAssetVariables(commonVariables);

	const metrics: Metric[] = [
		DownloadsMetric,
		ImpressionMadeMetric,
		CommentsMetric,
		RatingsMetric,
	];

	return (
		<MetricBaseCard
			{...props}
			emptyDescription={
				<>
					<span className="mr-1">
						{Liferay.Language.get(
							'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
						)}
					</span>

					<ClayLink
						href={URLConstants.VisitorBehaviorDocumentsAndMediaLink}
						key="DOCUMENTATION"
						target="_blank"
					>
						{Liferay.Language.get(
							'learn-more-about-visitor-behavior'
						)}
					</ClayLink>
				</>
			}
			emptyTitle={Liferay.Language.get(
				'there-are-no-visitors-data-found'
			)}
			metrics={metrics}
			queries={{
				MetricQuery: AssetMetricQuery(NAME),
				TabsQuery: AssetTabsQuery(metrics, NAME),

				name: NAME,
			}}
			reportContainer={ReportContainer.VisitorsBehaviorCard}
			variables={variables}
		/>
	);
};

export default DocumentsAndMediaMetricCard;
