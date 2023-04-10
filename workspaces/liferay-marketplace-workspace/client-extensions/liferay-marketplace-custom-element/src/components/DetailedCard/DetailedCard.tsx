import classNames from 'classnames';

import './DetailedCard.scss';

import {ReactNode} from 'react';

interface DetailedCardProps {
	cardIcon: string;
	cardIconAltText: string;
	cardTitle: string;
	children: ReactNode;
	sizing?: 'lg';
}

export function DetailedCard({
	cardIcon,
	cardIconAltText,
	cardTitle,
	children,
	sizing,
}: DetailedCardProps) {
	return (
		<div
			className={classNames('detailed-card-container', {
				'detailed-card-container-larger': sizing === 'lg',
			})}
		>
			<div className="detailed-card-header">
				<h2 className="">{cardTitle}</h2>

				<img alt={cardIconAltText} src={cardIcon}></img>
			</div>

			{children}
		</div>
	);
}
