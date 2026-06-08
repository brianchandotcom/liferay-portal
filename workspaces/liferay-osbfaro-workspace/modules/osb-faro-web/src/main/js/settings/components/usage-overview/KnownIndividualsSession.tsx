/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import moment from 'moment';
import React from 'react';
import {useTimeZone} from '~/shared/hooks/useTimeZone';
import {Colors} from '~/shared/util/charts';
import {CUSTOM_DATE_FORMAT, formatDateToTimeZone} from '~/shared/util/date';
import {sub} from '~/shared/util/lang';
import {toThousands} from '~/shared/util/numbers';
import {STATUS_DISPLAY_MAP} from '~/shared/util/subscriptions';

import {CurrentUsage} from './CurrentUsage';
import {UsageMetric} from './UsageMetric';
import {UsageMetricBarChart} from './UsageMetricBarChart';

interface IKnownIndividualsSessionProps {
	currentPlan: any;
}

export const KnownIndividualsSession = function KnownIndividualsSession({
	currentPlan,
}: IKnownIndividualsSessionProps) {
	const {timeZoneId} = useTimeZone();
	const {count, limit, status} = currentPlan.metrics.get('individuals');
	const syncedIndividualsCount =
		currentPlan.metrics.get('syncedIndividualsCount') ?? 0;
	const available = limit - count;

	return (
		<div className="mb-5 mt-4">
			<UsageMetric
				description={
					sub(
						Liferay.Language.get(
							'active-users-logged-on-your-dxp-instance-have-been-tracked-by-analytics-cloud-since-x'
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
				title={Liferay.Language.get('known-individuals')}
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
							label: Liferay.Language.get('known-individuals'),
							value: count,
						},
					}}
					legendText={sub(
						available === 1
							? Liferay.Language.get(
									'1-known-individual-is-available'
								)
							: Liferay.Language.get(
									'x-known-individuals-are-available'
								),
						[(available > 0 ? available : 0).toLocaleString()]
					)}
					limit={limit}
					percentageText={(percentage: number | string) =>
						sub(
							Liferay.Language.get(
								'x-known-individuals-were-used'
							),
							[Number(percentage)]
						) as string
					}
				/>

				<div className="mt-4">
					<div className="mb-1">
						<Text color="secondary" size={3}>
							{Liferay.Language.get(
								'individuals-breakdown'
							).toUpperCase()}
						</Text>
					</div>

					<UsageMetricBarChart
						items={{
							itemA: {
								color: Colors.mormont,
								label: sub(
									Liferay.Language.get(
										'individuals-synced-to-analytics-cloud-x'
									),
									[toThousands(syncedIndividualsCount)]
								) as string,
								value: syncedIndividualsCount,
							},
							itemB: {
								color: Colors.primary,
								label: sub(
									Liferay.Language.get('known-individuals-x'),
									[toThousands(count)]
								) as string,
								value: count,
							},
						}}
						total={syncedIndividualsCount + count}
					/>
				</div>
			</UsageMetric>
		</div>
	);
};
