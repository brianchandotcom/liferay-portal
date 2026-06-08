/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {graphql} from '@apollo/client/react/hoc';
import getDevicesMapper from '~/cerebro-shared/hocs/mappers/devices';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import {withDevicesCard} from '~/shared/hoc/DevicesCard';
import SiteDevicesQuery from '~/shared/queries/SiteDevicesQuery';
import URLConstants from '~/shared/util/url-constants';

/**
 * HOC
 * @description Site Devices
 */
const withSiteDevices = () =>
	graphql(
		SiteDevicesQuery,
		getDevicesMapper((result) => result.site.sessionsMetric)
	);

export default withDevicesCard(withSiteDevices, {
	documentationTitle: Liferay.Language.get(
		'learn-more-about-sessions-by-technology'
	),
	documentationUrl: URLConstants.SitesDashboardPagesSessionsByTechnology,
	reportContainer: ReportContainer.SessionTechnologyCard,
	title: Liferay.Language.get('there-are-no-sessions-on-the-selected-period'),
});
