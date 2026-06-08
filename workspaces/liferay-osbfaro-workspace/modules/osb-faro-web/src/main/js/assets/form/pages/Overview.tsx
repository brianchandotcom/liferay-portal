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
import FormMetricCard from '~/assets/form/components/FormMetricCard';
import DevicesCard from '~/assets/form/hocs/DevicesCard';
import FormAbandonmentCard from '~/assets/form/hocs/FormAbandonmentCard';
import LocationsCard from '~/assets/form/hocs/LocationsCard';
import AudienceReportCard from '~/shared/components/audience-report/AudienceReportBaseCard';
import {Name} from '~/shared/components/audience-report/types';
import {MetricName} from '~/shared/types/MetricName';
import {AssetTypes, ENABLE_FORM_ABANDONMENT} from '~/shared/util/constants';

const Overview = () => (
	<>
		<div className="row">
			<div className="col-sm-12">
				<FormMetricCard
					label={Liferay.Language.get('visitors-behavior')}
					legacyDropdownRangeKey={false}
				/>
			</div>
		</div>

		<div className="row">
			<div className="col-sm-12">
				<AudienceReportCard
					knownIndividualsTitle={Liferay.Language.get(
						'segmented-submissions'
					)}
					query={{
						metricName: MetricName.Submissions,
						name: Name.Form,
					}}
					segmentsTitle={Liferay.Language.get('submitter-segments')}
					uniqueVisitorsTitle={Liferay.Language.get('submissions')}
				/>
			</div>
		</div>

		<div className="row">
			<div className="col-lg-6 col-md-12">
				<LocationsCard
					label={Liferay.Language.get('submissions-by-location')}
					legacyDropdownRangeKey={false}
					metricLabel={Liferay.Language.get('submissions')}
				/>
			</div>

			<div className="col-lg-6 col-md-12">
				<DevicesCard
					label={Liferay.Language.get('submissions-by-technology')}
					legacyDropdownRangeKey={false}
					metricLabel={Liferay.Language.get('submissions')}
				/>
			</div>
		</div>

		{ENABLE_FORM_ABANDONMENT && (
			<div className="row">
				<div className="col-sm-12">
					<FormAbandonmentCard
						label={Liferay.Language.get('form-abandonment')}
						legacyDropdownRangeKey={false}
					/>
				</div>
			</div>
		)}

		<div className="row">
			<div className="col-sm-12">
				<AssetAppearsOnCard
					accessors={[
						Accessor.SubmissionsMetric,
						Accessor.ViewsMetric,
					]}
					assetType={AssetTypes.Form}
					emptyStateLink={EmptyStateLink.Form}
					emptyStateText={EmptyStateText.Form}
				/>
			</div>
		</div>
	</>
);

export default Overview;
