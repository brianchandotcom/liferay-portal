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

const TouchpointLocationsQuery = gql`
	query TouchpointLocationsQuery(
		$channelId: String
		$devices: String
		$experienceId: String
		$location: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$title: String
		$touchpoint: String
	) {
		page(
			channelId: $channelId
			canonicalUrl: $touchpoint
			country: $location
			deviceType: $devices
			experienceId: $experienceId
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			title: $title
		) {
			viewsMetric {
				...geolocationFragment
			}
		}
	}

	${GEOLOCATION_FRAGMENT}
`;

/**
 * HOC
 * @description Touchpoint Locations
 */
const withTouchpointLocations = () =>
	graphql(
		TouchpointLocationsQuery,
		getLocationsMapper((result) => result.page.viewsMetric)
	);

/**
 * HOC
 * @description Touchpoint Countries
 */
const withTouchpointsLocationsCountries = () =>
	graphql(
		TouchpointLocationsQuery,
		getLocationsMapperCountries((result) => result.page.viewsMetric)
	);

export default withLocationsCard(
	withTouchpointLocations,
	withTouchpointsLocationsCountries,
	{
		documentationTitle: Liferay.Language.get(
			'learn-more-about-views-by-location'
		),
		documentationUrl: URLConstants.SitesDashboardPagesViewsByLocation,
		reportContainer: ReportContainer.ViewsByLocationCard,
		title: Liferay.Language.get(
			'there-are-no-views-on-the-selected-period'
		),
	}
);
