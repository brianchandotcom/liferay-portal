/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {ReactElement} from 'react';

type InfoCardProps = {
	className?: string;
	growth?: number;
	growthContext?: string;
	limited?: boolean;
	symbol: string;
	title: string | ReactElement;
	value?: string | number;
};

const InfoCard: React.FC<InfoCardProps> = ({
	className,
	growth = 0,
	growthContext,
	limited = false,
	symbol,
	title,
	value,
}) => (
	<div
		className={classNames(
			'border d-flex flex-column info-card justify-content-between p-4',
			{
				limited,
			},
			className
		)}
	>
		<div className="align-content-center d-flex justify-content-between mb-2">
			<span className="d-flex flex-column">
				<span className="font-weight-lighter mb-2 mr-2 text-black-50">
					{title}
				</span>

				<span className="font-weight-bold h2">{value}</span>
			</span>

			<span className="d-flex flex-column justify-content-center">
				<ClayIcon
					className="text-primary"
					fontSize={32}
					symbol={symbol}
				/>
			</span>
		</div>

		{growthContext && (
			<div className="font-weight-bold text-black-50">
				<span
					className={classNames('mr-2', {
						'text-danger': growth < 0,
						'text-success': growth > 0,
					})}
				>
					<ClayIcon
						className="mr-21"
						symbol={
							growth > 0 ? 'order-arrow-up' : 'order-arrow-down'
						}
					/>
					{!isFinite(growth) ? 0 : growth}%
				</span>

				<span>{growthContext}</span>
			</div>
		)}
	</div>
);

export default InfoCard;
