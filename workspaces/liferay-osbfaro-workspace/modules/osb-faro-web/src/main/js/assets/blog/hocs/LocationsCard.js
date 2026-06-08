/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {graphql} from '@apollo/client/react/hoc';
import {withLocationsCard} from '~/cerebro-shared/hocs/LocationsCard';
import getLocationsMapper, {
	getLocationsMapperCountries,
} from '~/cerebro-shared/hocs/mappers/locations';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import {GEOLOCATION_FRAGMENT} from '~/shared/queries/fragments';
import URLConstants from '~/shared/util/url-constants';

const GEOLOCATION_QUERY = gql`
	query BlogsMetrics(
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
		blog(
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
			viewsMetric {
				...geolocationFragment

				previousValue
				value
			}
		}
	}

	${GEOLOCATION_FRAGMENT}
`;

/**
 * HOC
 * @description Blogs Locations
 */
const withBlogsLocations = () =>
	graphql(
		GEOLOCATION_QUERY,
		getLocationsMapper((result) => result.blog.viewsMetric)
	);

/**
 * HOC
 * @description Blogs Countries
 */
const withBlogsLocationsCountries = () =>
	graphql(
		GEOLOCATION_QUERY,
		getLocationsMapperCountries((result) => result.blog.viewsMetric)
	);

export default withLocationsCard(
	withBlogsLocations,
	withBlogsLocationsCountries,
	{
		documentationTitle: Liferay.Language.get(
			'learn-more-about-views-by-location'
		),
		documentationUrl: URLConstants.SitesDashboardBlogsViewsByLocation,
		reportContainer: ReportContainer.ViewsByLocationCard,
		title: Liferay.Language.get(
			'there-are-no-views-on-the-selected-period'
		),
	}
);
