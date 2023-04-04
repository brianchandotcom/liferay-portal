import './DetailedCard.scss';

import {ReactNode} from 'react';

interface DetailedCardProps {
	cardIcon: string;
	cardIconAltText: string;
	cardTitle: string;
	children: ReactNode;
}

export function DetailedCard({
	cardIcon,
	cardIconAltText,
	cardTitle,
	children,
}: DetailedCardProps) {
	return (
		<div className="detailed-card-container">
			<div className="detailed-card-header">
				<h2 className="">{cardTitle}</h2>

				<img alt={cardIconAltText} src={cardIcon}></img>
			</div>

			{children}
		</div>
	);
}
