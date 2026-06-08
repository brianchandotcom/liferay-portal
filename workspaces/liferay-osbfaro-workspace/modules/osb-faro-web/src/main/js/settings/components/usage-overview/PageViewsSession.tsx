/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moment from 'moment';
import React from 'react';
import {useTimeZone} from '~/shared/hooks/useTimeZone';
import {Colors} from '~/shared/util/charts';
import {CUSTOM_DATE_FORMAT, formatDateToTimeZone} from '~/shared/util/date';
import {sub} from '~/shared/util/lang';
import {STATUS_DISPLAY_MAP} from '~/shared/util/subscriptions';

import {CurrentUsage} from './CurrentUsage';
import {UsageMetric} from './UsageMetric';

interface IPageViewsSessionProps {
	currentPlan: any;
}

export const PageViewsSession = function PageViewsSession({
	currentPlan,
}: IPageViewsSessionProps) {
	const {timeZoneId} = useTimeZone();
	const {count, limit, status} = currentPlan.metrics.get('pageViews');
	const available = limit - count;

	return (
		<UsageMetric
			description={
				sub(
					Liferay.Language.get(
						'total-page-views-have-been-tracked-by-analytics-cloud-since-x'
					),
					[
						formatDateToTimeZone(
							moment(currentPlan.startDate),
							CUSTOM_DATE_FORMAT,
							timeZoneId
						),
					]
				) as string
			}
			title={Liferay.Language.get('page-views')}
		>
			<CurrentUsage
				count={count}
				items={{
					itemA: {
						color: (Colors as {[key: string]: any})[
							(STATUS_DISPLAY_MAP as {[key: string]: string})[
								status
							]
						],
						label: Liferay.Language.get('page-views'),
						value: count,
					},
				}}
				legendText={sub(
					Liferay.Language.get('x-page-views-are-available'),
					[(available > 0 ? available : 0).toLocaleString()]
				)}
				limit={limit}
				percentageText={(percentage: number | string) =>
					Number(percentage) === 1
						? Liferay.Language.get('1-page-view-was-used')
						: Liferay.Language.get('x-page-views-were-used')
				}
			/>
		</UsageMetric>
	);
};
