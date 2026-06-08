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

const BROWSER_DEVICE_QUERY = gql`
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
 * @description Blogs Devices
 */
const withBlogsDevices = () =>
	graphql(
		BROWSER_DEVICE_QUERY,
		getDevicesMapper((result) => result.blog.viewsMetric)
	);

export default withDevicesCard(withBlogsDevices, {
	documentationTitle: Liferay.Language.get(
		'learn-more-about-views-by-technology'
	),
	documentationUrl: URLConstants.SitesDashboardBlogsViewsByTechnology,
	reportContainer: ReportContainer.ViewsByTechnologyCard,
	title: Liferay.Language.get('there-are-no-views-on-the-selected-period'),
});
