import noApp from '../../assets/images/no_app.png';

import './DashboardEmptyTable.scss';

export function DashboardEmptyTable({
	description1,
	description2,
	icon,
	title,
}: {
	description1: string;
	description2: string;
	icon: string;
	title: string;
}) {
	return (
		<div className="dashboard-empty-state">
			<div className="dashboard-empty-state-background">
				<img
					alt={title}
					className="dashboard-empty-state-image"
					src={icon}
				/>
			</div>

			<div className="dashboard-empty-state-title">{title}</div>

			<div className="dashboard-empty-state-description">
				{description1 && (
					<span className="dashboard-empty-state-description-first">
						{description1}
					</span>
				)}

				{description2 && <span> {description2}</span>}
			</div>
		</div>
	);
}
