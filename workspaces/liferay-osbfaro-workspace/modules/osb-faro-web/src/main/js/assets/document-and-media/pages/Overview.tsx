/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {
	Accessor,
	AssetAppearsOnCard,
	EmptyStateLink,
	EmptyStateText,
} from '~/assets/components/AssetAppearsOnCard';
import DocumentsAndMediaMetricCard from '~/assets/document-and-media/components/DocumentsAndMediaMetricCard';
import DevicesCard from '~/assets/document-and-media/hocs/DevicesCard';
import LocationsCard from '~/assets/document-and-media/hocs/LocationsCard';
import AudienceReportCard from '~/shared/components/audience-report/AudienceReportBaseCard';
import {Name} from '~/shared/components/audience-report/types';
import {MetricName} from '~/shared/types/MetricName';
import {AssetTypes} from '~/shared/util/constants';

const Overview = () => (
	<>
		<div className="row">
			<div className="col-sm-12">
				<DocumentsAndMediaMetricCard
					label={Liferay.Language.get('visitors-behavior')}
				/>
			</div>
		</div>

		<div className="row">
			<div className="col-sm-12">
				<AudienceReportCard
					knownIndividualsTitle={Liferay.Language.get(
						'segmented-downloads'
					)}
					query={{
						metricName: MetricName.Downloads,
						name: Name.Document,
					}}
					segmentsTitle={Liferay.Language.get('downloaded-segments')}
					uniqueVisitorsTitle={Liferay.Language.get('downloads')}
				/>
			</div>
		</div>

		<div className="row">
			<div className="col-lg-6 col-md-12">
				<LocationsCard
					label={Liferay.Language.get('downloads-by-location')}
					legacyDropdownRangeKey={false}
					metricLabel={Liferay.Language.get('downloads')}
				/>
			</div>

			<div className="col-lg-6 col-md-12">
				<DevicesCard
					label={Liferay.Language.get('downloads-by-technology')}
					legacyDropdownRangeKey={false}
					metricLabel={Liferay.Language.get('downloads')}
				/>
			</div>
		</div>

		<div className="row">
			<div className="col-sm-12">
				<AssetAppearsOnCard
					accessors={[
						Accessor.DownloadsMetric,
						Accessor.ImpressionMadeMetric,
					]}
					assetType={AssetTypes.Document}
					emptyStateLink={EmptyStateLink.Document}
					emptyStateText={EmptyStateText.Document}
				/>
			</div>
		</div>
	</>
);

export default Overview;
