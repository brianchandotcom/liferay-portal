/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import BlogMetricCard from '~/assets/blog/components/BlogMetricCard';
import DevicesCard from '~/assets/blog/hocs/DevicesCard';
import LocationsCard from '~/assets/blog/hocs/LocationsCard';
import {
	Accessor,
	AssetAppearsOnCard,
	EmptyStateLink,
	EmptyStateText,
} from '~/assets/components/AssetAppearsOnCard';
import AudienceReportCard from '~/shared/components/audience-report/AudienceReportBaseCard';
import {Name} from '~/shared/components/audience-report/types';
import {MetricName} from '~/shared/types/MetricName';
import {AssetTypes} from '~/shared/util/constants';

const Overview = () => (
	<>
		<div className="row">
			<div className="col-sm-12">
				<BlogMetricCard
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
						name: Name.Blog,
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
					assetType={AssetTypes.Blog}
					emptyStateLink={EmptyStateLink.Blog}
					emptyStateText={EmptyStateText.Blog}
				/>
			</div>
		</div>
	</>
);

export default Overview;
