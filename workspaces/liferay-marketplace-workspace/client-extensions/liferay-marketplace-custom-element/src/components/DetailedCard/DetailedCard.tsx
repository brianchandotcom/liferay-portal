/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
