/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {OperationOption, graphql} from '@apollo/client/react/hoc';
import {withLocationsCard} from '~/cerebro-shared/hocs/LocationsCard';
import getLocationsMapper, {
	getLocationsMapperCountries,
} from '~/cerebro-shared/hocs/mappers/locations';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import {GEOLOCATION_FRAGMENT} from '~/shared/queries/fragments';
import URLConstants from '~/shared/util/url-constants';

type JournalMetricResult = {
	journal: {
		viewsMetric: unknown;
	};
};

const GEOLOCATION_QUERY = gql`
	query WebContentMetrics(
		$assetId: String!
		$channelId: String
		$devices: String
		$location: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$title: String
		$touchpoint: String
	) {
		journal(
			assetId: $assetId
			canonicalUrl: $touchpoint
			channelId: $channelId
			country: $location
			deviceType: $devices
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			title: $title
		) {
			assetId
			assetTitle
			urls
			viewsMetric {
				...geolocationFragment
			}
		}
	}

	${GEOLOCATION_FRAGMENT}
`;

/**
 * HOC
 * @description Web Content Locations
 */
const withWebContentLocations = () =>
	graphql(
		GEOLOCATION_QUERY,
		getLocationsMapper(
			(result: JournalMetricResult) => result.journal.viewsMetric
		) as OperationOption<object, object>
	);

/**
 * HOC
 * @description Web Content Countries
 */
const withWebContentLocationsCountries = () =>
	graphql(
		GEOLOCATION_QUERY,
		getLocationsMapperCountries(
			(result: JournalMetricResult) => result.journal.viewsMetric
		) as OperationOption<object, object>
	);

export default withLocationsCard(
	withWebContentLocations,
	withWebContentLocationsCountries,
	{
		documentationTitle: Liferay.Language.get(
			'learn-more-about-views-by-location'
		),
		documentationUrl: URLConstants.SitesDashboardWebContentViewsByLocation,
		reportContainer: ReportContainer.ViewsByLocationCard,
		title: Liferay.Language.get(
			'there-are-no-views-on-the-selected-period'
		),
	}
);
