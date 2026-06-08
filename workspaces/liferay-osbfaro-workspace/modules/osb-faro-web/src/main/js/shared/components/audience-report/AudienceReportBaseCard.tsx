/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import BaseCard from '~/shared/components/base-card';

import Card from '../Card';
import {ReportContainer} from '../download-report/DownloadPDFReport';
import AudienceReport from './AudienceReport';
import {AssetAudienceReportQuery, PageAudienceReportQuery} from './queries';
import {IAudienceReportBaseCardProps, Name} from './types';

function AudienceReportBaseCard({
	query: {metricName, name},
	...props
}: IAudienceReportBaseCardProps) {
	const AudienceReportQuery =
		name === Name.Page ? PageAudienceReportQuery : AssetAudienceReportQuery;

	return (
		<BaseCard
			className="analytics-audience-report-card"
			label={Liferay.Language.get('audience')}
			legacyDropdownRangeKey={false}
			minHeight={536}
			reportContainer={ReportContainer.AudienceCard}
		>
			{({experienceId, filters, rangeSelectors}) => (
				<Card.Body>
					<AudienceReport
						{...props}
						Query={AudienceReportQuery({
							metricName,
							name,
						})}
						experienceId={experienceId}
						filters={filters}
						mapper={(result: any) => result?.[name]?.[metricName]}
						name={name}
						rangeSelectors={rangeSelectors}
					/>
				</Card.Body>
			)}
		</BaseCard>
	);
}

export default AudienceReportBaseCard;
