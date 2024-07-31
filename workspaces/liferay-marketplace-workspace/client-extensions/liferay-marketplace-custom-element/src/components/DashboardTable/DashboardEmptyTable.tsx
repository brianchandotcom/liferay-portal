/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

import './DashboardEmptyTable.scss';

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';

export function DashboardEmptyTable({
	button,
	buttonName,
	children,
	className,
	description1,
	description2,
	icon,
	title,
}: {
	button?: boolean;
	buttonName?: string;
	children?: ReactNode;
	className?: string;
	description1?: string;
	description2?: string;
	icon: string;
	title: string;
}) {
	return (
		<div className="dashboard-empty-state py-6">
			<div className="dashboard-empty-state-background">
				<ClayIcon
					aria-label={title}
					className="dashboard-empty-state-image"
					symbol={icon}
				/>
			</div>

			<div className="dashboard-empty-state-title h4">{title}</div>

			<div className="dashboard-empty-state-description">
				{description1 && (
					<span className="dashboard-empty-state-description-first">
						{description1}
					</span>
				)}

				{description2 && (
					<span className={className}>{description2}</span>
				)}
			</div>

			{children}

			<div>
				{button && (
					<ClayButton className="dashboard-empty-state-button">
						{buttonName}
					</ClayButton>
				)}
			</div>
		</div>
	);
}
