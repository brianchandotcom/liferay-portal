/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import useMarketplaceSpringBootOAuth2 from '../../../hooks/useMarketplaceSpringBootOAuth2';
import {colors} from '../mock';

const useAnalyticsViewsMetrics = () => {
	const marketplaceSpringBootOAuth2 = useMarketplaceSpringBootOAuth2();

	const {data: analyticsViewsResposne} = useSWR<AnalyticsViews>(
		'administrator-dashboard/metrics/analytics/views',
		async () => {
			const response = await marketplaceSpringBootOAuth2.getAnalitcsPages(
				new URLSearchParams({
					keywords: '/p/',
					sortMetric: 'visitorsMetric',
					sortOrder: 'desc',
				})
			);

			return response;
		}
	);

	const viewsMetrics =
		analyticsViewsResposne?.results.map((item) => ({
			title: item.title.split('-')[0].trim(),
			views: item.metrics.viewsMetric.value,
			visitor: item.metrics.visitorsMetric.value,
		})) || [];

	viewsMetrics?.splice(5);

	return {
		data: {
			colors: {
				'Total Views': colors.color1,
				'Unique Visitors': colors.color2,
			},
			columns: [
				['x', ...viewsMetrics?.map((page) => page.title)],
				['Total Views', ...viewsMetrics?.map((page) => page.views)],
				[
					'Unique Visitors',
					...viewsMetrics?.map((page) => page.visitor),
				],
			],
			viewsMetrics,
		},
	};
};

export default useAnalyticsViewsMetrics;
