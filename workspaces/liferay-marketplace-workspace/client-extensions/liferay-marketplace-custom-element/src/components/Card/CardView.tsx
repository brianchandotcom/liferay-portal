/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

import './CardView.scss';

import ClayIcon from '@clayui/icon';

import {Tooltip} from '../Tooltip/Tooltip';

interface CardViewProps {
	children?: ReactNode;
	description: string;
	icon?: string;
	title: string;
	tooltip?: string;
}

export function CardView({
	children,
	description,
	icon,
	title,
	tooltip,
}: CardViewProps) {
	return (
		<div className="card-view-container">
			<div className="card-view-main-info">
				<div className="card-view-title">
					<span className="card-view-title-text">{title}</span>

					{icon && (
						<ClayIcon
							aria-label="Icon"
							className="card-view-title-icon"
							symbol={icon}
						/>
					)}
				</div>
				{icon && (
					<button className="card-view-learn-more">
						<span className="card-view-learn-more-text">
							Learn more
						</span>
					</button>
				)}

				{tooltip && (
					<div className="field-base-tooltip ml-3">
						<Tooltip tooltip={tooltip} />
					</div>
				)}
			</div>

			<span className="card-view-description">{description}</span>

			{children}
		</div>
	);
}
