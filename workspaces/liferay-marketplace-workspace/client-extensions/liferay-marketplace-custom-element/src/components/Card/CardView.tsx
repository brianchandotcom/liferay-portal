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

import {ReactNode} from 'react';

import './CardView.scss';

interface CardViewProps {
	children?: ReactNode;
	description: string;
	icon?: string;
	title: string;
}

export function CardView({children, description, icon, title}: CardViewProps) {
	return (
		<div className="card-view-container">
			<div className="card-view-main-info">
				<div className="card-view-title">
					<span className="card-view-title-text">{title}</span>

					<img
						alt="Icon"
						className="card-view-title-icon"
						src={icon}
					/>
				</div>

				<button className="card-view-learn-more">
					<span className="card-view-learn-more-text">
						Learn more
					</span>
				</button>
			</div>

			<span className="card-view-description">{description}</span>

			{children}
		</div>
	);
}
