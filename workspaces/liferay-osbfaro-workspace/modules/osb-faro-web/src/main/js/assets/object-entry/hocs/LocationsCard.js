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
	query ObjectEntryLocationsMetric(
		$assetId: String!
		$devices: String
		$location: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$touchpoint: String
	) {
		objectEntry(
			assetId: $assetId
			canonicalUrl: $touchpoint
			country: $location
			deviceType: $devices
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
		) {
			assetId
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
 * @description ObjectEntry Locations
 */
const withObjectEntryLocations = () =>
	graphql(
		GEOLOCATION_QUERY,
		getLocationsMapper((result) => result.objectEntry.viewsMetric)
	);

/**
 * HOC
 * @description ObjectEntry Countries
 */
const withObjectEntryLocationsCountries = () =>
	graphql(
		GEOLOCATION_QUERY,
		getLocationsMapperCountries((result) => result.objectEntry.viewsMetric)
	);

export default withLocationsCard(
	withObjectEntryLocations,
	withObjectEntryLocationsCountries,
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
