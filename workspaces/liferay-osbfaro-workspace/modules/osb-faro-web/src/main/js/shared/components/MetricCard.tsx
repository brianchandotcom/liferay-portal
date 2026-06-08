/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {isNil} from 'lodash';
import React, {ReactNode} from 'react';
import {TrendClassification} from '~/segment/types';
import Card from '~/shared/components/Card';
import Loading from '~/shared/components/Loading';
import {getIcon, getStatsColor} from '~/shared/util/metrics';
import {toRounded} from '~/shared/util/numbers';

interface IMetricCardTrend {
	percentage: number;
	trendClassification: TrendClassification;
}

interface IMetricCardProps {
	className?: string;
	description: string;
	loading?: boolean;
	minHeight?: number;
	renderTrendLabel: (percentageNode: ReactNode) => ReactNode;
	title: string;
	trend?: IMetricCardTrend;
	trendClassName?: string;
	value: ReactNode;
}

const MetricCard: React.FC<IMetricCardProps> = ({
	className,
	description,
	loading = false,
	minHeight,
	renderTrendLabel,
	title,
	trend,
	trendClassName,
	value,
}) => {
	if (loading) {
		return (
			<Card
				className={classNames(className, 'flex-fill p-3 w-100')}
				minHeight={minHeight}
			>
				<Card.Body>
					<Loading />
				</Card.Body>
			</Card>
		);
	}

	const percentageColor = getStatsColor(trend?.trendClassification || '');

	return (
		<Card
			className={classNames(className, 'flex-fill p-3 w-100')}
			minHeight={minHeight}
		>
			<Card.Title>
				<div className="text-uppercase text-weight-semi-bold">
					<Text>{title}</Text>
				</div>
			</Card.Title>

			<Card.Body className="d-flex justify-content-between" noPadding>
				<div className="mt-2">
					<Text color="secondary" size={3}>
						{description}
					</Text>
				</div>

				<div>
					<div className="mt-2 text-lowercase text-weight-semi-bold">
						<Text size={7}>{value}</Text>
					</div>

					<div
						className={classNames('text-secondary', trendClassName)}
					>
						{!isNil(trend?.trendClassification) &&
							trend?.trendClassification !==
								TrendClassification.Neutral && (
								<ClayIcon
									style={{color: percentageColor}}
									symbol={
										getIcon(trend?.percentage ?? 0) ?? ''
									}
								/>
							)}

						{renderTrendLabel(
							<span
								className="mr-1"
								key="percentage"
								style={{color: percentageColor}}
							>
								{`${toRounded(
									Math.abs(trend?.percentage ?? 0),
									2
								)}%`}
							</span>
						)}
					</div>
				</div>
			</Card.Body>
		</Card>
	);
};

export default MetricCard;
