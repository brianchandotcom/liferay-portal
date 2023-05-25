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

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';

import './GateCard.scss';

interface Link {
	href: string;
	label: string;
}

interface Image {
	description: string;
	svg: string;
}

interface GateCard {
	description: string;
	image: Image;
	label?: string;
	link?: Link;
	title: string;
}

export function GateCard({description, image, label, link, title}: GateCard) {
	return (
		<div className="gate-card-container">
			<div>
				<img
					alt={image.description}
					className="gate-card-image"
					src={image.svg}
				/>
			</div>

			<div className="gate-card-body">
				<div className="gate-card-title-container">
					<h2 className="gate-card-title">{title}</h2>

					{label && <div className="gate-card-label">{label}</div>}
				</div>

				<div>
					<h3 className="gate-card-description">{description}</h3>
				</div>

				{link && (
					<ClayLink className="gate-card-link" href={link.href}>
						{link.label}

						<ClayIcon
							className="gate-card-icon"
							symbol="order-arrow-right"
						/>
					</ClayLink>
				)}
			</div>
		</div>
	);
}
