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
import WebContentMetricCard from '~/assets/web-content/components/WebContentMetricCard';
import DevicesCard from '~/assets/web-content/hocs/DevicesCard';
import LocationsCard from '~/assets/web-content/hocs/LocationsCard';
import AudienceReportCard from '~/shared/components/audience-report/AudienceReportBaseCard';
import {Name} from '~/shared/components/audience-report/types';
import {MetricName} from '~/shared/types/MetricName';
import {AssetTypes} from '~/shared/util/constants';

const Overview = () => (
	<>
		<div className="row">
			<div className="col-sm-12">
				<WebContentMetricCard
					label={Liferay.Language.get('visitors-behavior')}
				/>
			</div>
		</div>

		<div className="row">
			<div className="col-sm-12">
				<AudienceReportCard
					knownIndividualsTitle={Liferay.Language.get(
						'segmented-views'
					)}
					query={{
						metricName: MetricName.Views,
						name: Name.Journal,
					}}
					uniqueVisitorsTitle={Liferay.Language.get('views')}
				/>
			</div>
		</div>

		<div className="row">
			<div className="col-lg-6 col-md-12">
				<LocationsCard
					label={Liferay.Language.get('views-by-location')}
					legacyDropdownRangeKey={false}
				/>
			</div>
			<div className="col-lg-6 col-md-12">
				<DevicesCard
					label={Liferay.Language.get('views-by-technology')}
					legacyDropdownRangeKey={false}
				/>
			</div>
		</div>

		<div className="row">
			<div className="col-sm-12">
				<AssetAppearsOnCard
					accessors={[Accessor.ViewsMetric]}
					assetType={AssetTypes.Journal}
					emptyStateLink={EmptyStateLink.Journal}
					emptyStateText={EmptyStateText.Journal}
				/>
			</div>
		</div>
	</>
);

export default Overview;
