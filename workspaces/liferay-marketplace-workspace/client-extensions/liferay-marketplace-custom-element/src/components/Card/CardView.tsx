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
