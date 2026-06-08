/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {OperationOption, graphql} from '@apollo/client/react/hoc';
import getDevicesMapper from '~/cerebro-shared/hocs/mappers/devices';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import {withDevicesCard} from '~/shared/hoc/DevicesCard';
import {BROWSER_FRAGMENT, DEVICE_FRAGMENT} from '~/shared/queries/fragments';
import URLConstants from '~/shared/util/url-constants';

type DocumentMetricResult = {
	document: {
		downloadsMetric: unknown;
	};
};

const BROWSER_DEVICE_QUERY = gql`
	query DocumentsAndMediaMetrics(
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
		document(
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
			downloadsMetric {
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
 * @description Documents And Media Devices
 */
const withDocumentsAndMediaDevices = () =>
	graphql(
		BROWSER_DEVICE_QUERY,
		getDevicesMapper(
			(result: DocumentMetricResult) => result.document.downloadsMetric
		) as OperationOption<object, object>
	);

export default withDevicesCard(withDocumentsAndMediaDevices, {
	documentationTitle: Liferay.Language.get(
		'learn-more-about-downloads-by-technology'
	),
	documentationUrl:
		URLConstants.SitesDashboardDocumentsAndMediaViewsByTechnology,
	reportContainer: ReportContainer.DownloadsByTechnologyCard,
	title: Liferay.Language.get(
		'there-are-no-downloads-on-the-selected-period'
	),
});
