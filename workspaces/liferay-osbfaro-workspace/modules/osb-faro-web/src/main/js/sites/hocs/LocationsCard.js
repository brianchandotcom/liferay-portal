/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {graphql} from '@apollo/client/react/hoc';
import {withLocationsCard} from '~/cerebro-shared/hocs/LocationsCard';
import getLocationsMapper, {
	getLocationsMapperCountries,
} from '~/cerebro-shared/hocs/mappers/locations';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import SessionLocationsQuery from '~/shared/queries/SessionLocationsQuery';
import URLConstants from '~/shared/util/url-constants';

/**
 * HOC
 * @description Site Locations
 */
const withSiteLocations = () =>
	graphql(
		SessionLocationsQuery,
		getLocationsMapper((result) => result.site.sessionsMetric)
	);

/**
 * HOC
 * @description Site Countries
 */
const withSiteLocationsCountries = () =>
	graphql(
		SessionLocationsQuery,
		getLocationsMapperCountries((result) => result.site.sessionsMetric)
	);

export default withLocationsCard(
	withSiteLocations,
	withSiteLocationsCountries,
	{
		documentationTitle: Liferay.Language.get(
			'learn-more-about-sessions-by-location'
		),
		documentationUrl: URLConstants.SitesDashboardPagesSessionsByLocation,
		reportContainer: ReportContainer.SessionsByLocationCard,
		title: Liferay.Language.get(
			'there-are-no-sessions-on-the-selected-period'
		),
	}
);
