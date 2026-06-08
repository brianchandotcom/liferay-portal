/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {graphql} from '@apollo/client/react/hoc';
import getDevicesMapper from '~/cerebro-shared/hocs/mappers/devices';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import {withDevicesCard} from '~/shared/hoc/DevicesCard';
import {BROWSER_FRAGMENT, DEVICE_FRAGMENT} from '~/shared/queries/fragments';
import URLConstants from '~/shared/util/url-constants';

const TouchpointDevicesQuery = gql`
	query TouchpointDevicesQuery(
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
				...browserFragment
				...deviceFragment

				previousValue
				value
			}
		}
	}

	${BROWSER_FRAGMENT}
	${DEVICE_FRAGMENT}
`;

/**
 * HOC
 * @description Touchpoint Devices
 */
const withTouchpointDevices = () =>
	graphql(
		TouchpointDevicesQuery,
		getDevicesMapper((result) => result.page.viewsMetric)
	);

export default withDevicesCard(withTouchpointDevices, {
	documentationTitle: Liferay.Language.get(
		'learn-more-about-views-by-technology'
	),
	documentationUrl: URLConstants.SitesDashboardPagesViewsByTechnology,
	reportContainer: ReportContainer.ViewsByTechnologyCard,
	title: Liferay.Language.get('there-are-no-views-on-the-selected-period'),
});
