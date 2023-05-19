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
