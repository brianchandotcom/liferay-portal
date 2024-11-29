/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';

import './ProgressBarContent.css';

import classNames from 'classnames';

interface IProps {
	displayUsage?: boolean;
	maxCount?: number;
	title: string;
	usedCount?: number;
}

const ProgressBarContent: React.FC<IProps> = ({
	displayUsage,
	maxCount = 0,
	title,
	usedCount = 0,
}) => {
	const barPercentage = useMemo(() => {
		if (displayUsage) {
			return `${(usedCount / maxCount) * 100}%`;
		}

		return `${Math.random() * 100}%`;
	}, [displayUsage, maxCount, usedCount]);

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
						{displayUsage && usedCount.toLocaleString()}
					</h3>

					{displayUsage && (
						<span className="total-value-text">
							of {maxCount.toLocaleString()}
						</span>
					)}
				</div>

				<div className="bar-container overflow-hidden">
					<div
						className="bar-content"
						style={{
							width: barPercentage,
						}}
					/>
				</div>
			</div>
		</div>
	);
};

export default ProgressBarContent;
