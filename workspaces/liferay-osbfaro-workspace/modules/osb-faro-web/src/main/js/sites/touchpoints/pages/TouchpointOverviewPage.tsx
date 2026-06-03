import AssetsListCard from 'sites/touchpoints/hocs/AssetsListCard';
import AudienceReportCard from 'shared/components/audience-report/AudienceReportBaseCard';
import DevicesCard from 'sites/touchpoints/hocs/DevicesCard';
import LocationsCard from 'sites/touchpoints/hocs/LocationsCard';
import PageMetricCard from 'sites/touchpoints/components/PageMetricCard';
import React from 'react';
import {ENABLE_ASSET_CARD} from 'shared/util/constants';
import {MetricName} from 'shared/types/MetricName';
import {Name} from 'shared/components/audience-report/types';

const TouchpointOverviewPage = () => (
	<>
		<div className='row'>
			<div className='col-sm-12'>
				<PageMetricCard
					label={Liferay.Language.get('visitors-behavior')}
				/>
			</div>
		</div>

		<div className='row'>
			<div className='col-sm-12'>
				<AudienceReportCard
					knownIndividualsTitle={Liferay.Language.get(
						'segmented-viewers'
					)}
					query={{
						metricName: MetricName.Views,
						name: Name.Page
					}}
					segmentsTitle={Liferay.Language.get('viewer-segments')}
					uniqueVisitorsTitle={Liferay.Language.get('visitors')}
				/>
			</div>
		</div>

		<div className='row'>
			<div className='col-lg-6 col-md-12'>
				<LocationsCard
					label={Liferay.Language.get('views-by-location')}
					legacyDropdownRangeKey={false}
				/>
			</div>

			<div className='col-lg-6 col-md-12'>
				<DevicesCard
					label={Liferay.Language.get('views-by-technology')}
					legacyDropdownRangeKey={false}
				/>
			</div>
		</div>

		{ENABLE_ASSET_CARD && (
			<div className='row'>
				<div className='col-lg-6 col-md-12'>
					<AssetsListCard
						label={Liferay.Language.get('displayed-assets')}
					/>
				</div>
			</div>
		)}
	</>
);

export default TouchpointOverviewPage;
