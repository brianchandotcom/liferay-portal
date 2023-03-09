import './AccountAndAppCard.scss';

interface AppCardProps {
	category: string;
	logo: string;
	title: string;
}

export function AccountAndAppCard({category, logo, title}: AppCardProps) {
	return (
		<div className="card-container">
			<div className="card-logo">
				<img alt="logo" className="card-logo-image" src={logo} />
			</div>

			<div className="card-info">
				<span className="card-info-description">{category}</span>

				<span className="card-info-text">{title}</span>
			</div>
		</div>
	);
}
