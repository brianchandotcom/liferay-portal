/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';

import './ProgressBarContent.css';

import classNames from 'classnames';
import i18n from '~/utils/I18n';

interface IProps {
	displayUsage?: boolean;
	maxCount?: number;
	maxCountUnits?: string;
	percentage?: string;
	title: string;
	usedCount?: number;
	usedCountUnits?: string;
}

const ProgressBarContent: React.FC<IProps> = ({
	displayUsage,
	maxCount,
	maxCountUnits = '',
	percentage,
	title,
	usedCount,
	usedCountUnits = '',
}) => {
	const barPercentage = useMemo(() => {
		if (displayUsage) {
			if (percentage !== undefined) {
				return `${percentage}%`;
			}

			return '0%';
		}

		return `${Math.random() * 100}%`;
	}, [displayUsage, percentage]);

	const isUnlimited = maxCount !== undefined && maxCount < 0;

	return (
		<div className="progress-bar-content w-100">
			<h5 className="mb-3">{title}</h5>

			<div>
				<div className="align-items-end d-flex justify-content-between mb-2">
					<h3
						className={classNames('m-0', {
							'col-3 empty-text': !displayUsage,
						})}
					>
						{displayUsage &&
							(usedCount !== undefined && maxCount !== undefined
								? usedCount.toLocaleString() + usedCountUnits
								: '-')}
					</h3>

					{displayUsage && (
						<span className="total-value-text">
							{isUnlimited
								? i18n.translate('of-unlimited')
								: `${i18n.translate('of')} ${
										maxCount !== undefined
											? maxCount.toLocaleString() +
												maxCountUnits
											: '-'
									}`}
						</span>
					)}
				</div>

				<div className="bar-container overflow-hidden">
					<div
						className="bar-content"
						style={{
							width: isUnlimited ? 0 : barPercentage,
						}}
					/>
				</div>
			</div>
		</div>
	);
};

export default ProgressBarContent;
