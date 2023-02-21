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
