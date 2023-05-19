/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import noApp from '../../assets/images/no_app.png';

import './DashboardEmptyTable.scss';

export function DashboardEmptyTable({
	description1,
	description2,
	icon,
	title,
}: {
	description1: string;
	description2: string;
	icon: string;
	title: string;
}) {
	return (
		<div className="dashboard-empty-state">
			<div className="dashboard-empty-state-background">
				<img
					alt={title}
					className="dashboard-empty-state-image"
					src={icon}
				/>
			</div>

			<div className="dashboard-empty-state-title">{title}</div>

			<div className="dashboard-empty-state-description">
				{description1 && (
					<span className="dashboard-empty-state-description-first">
						{description1}
					</span>
				)}

				{description2 && <span> {description2}</span>}
			</div>
		</div>
	);
}
