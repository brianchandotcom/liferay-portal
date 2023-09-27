/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './CardLink.scss';

interface CardLinkProps {
	description: string;
	icon?: string;
	title: string;
}

export function CardLink({description, icon, title}: CardLinkProps) {
	return (
		<div className="card-link-container">
			<div className="card-link-main-info">
				<div className="card-link-icon">
					<img
						alt="Icon"
						className="card-link-icon-image"
						src={icon}
					/>
				</div>

				<div className="card-link-info">
					<span className="card-link-info-text">{title}</span>

					<a className="card-link-info-description" href="#">
						{description}
					</a>
				</div>
			</div>
		</div>
	);
}
