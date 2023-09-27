/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
