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

import './DetailedCard.scss';

import ClayIcon from '@clayui/icon';
import {ReactNode} from 'react';

interface DetailedCardProps {
	cardIcon?: string;
	cardIconAltText: string;
	cardTitle: string;
	children: ReactNode;
	clayIcon?: string;
	sizing?: 'lg';
}

export function DetailedCard({
	cardIcon,
	cardIconAltText,
	cardTitle,
	children,
	clayIcon,
}: DetailedCardProps) {
	return (
		<div className="detailed-card-container">
			<div className="detailed-card-header">
				<h2 className="">{cardTitle}</h2>

				<div className="detailed-card-header-icon-container">
					{clayIcon ? (
						<ClayIcon
							className="detailed-card-header-clay-icon"
							symbol={clayIcon}
						/>
					) : (
						<img alt={cardIconAltText} src={cardIcon} />
					)}
				</div>
			</div>

			{children}
		</div>
	);
}
