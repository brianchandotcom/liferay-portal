import ClayIcon from '@clayui/icon';

import './GateCard.scss';

interface GateCard {
	description: string;
	image: {
		description: string;
		svg: string;
	};
	label?: string;
	link: string;
	title: string;
}

export function GateCard({description, image, label, link, title}: GateCard) {
	return (
		<div className="card-container">
			<div>
				<img
					alt={image.description}
					className="card-image"
					src={image.svg}
				/>
			</div>

			<div className="card-body">
				<div className="card-title-container">
					<h2 className="card-title">{title}</h2>

					{label && <div className="card-label">{label}</div>}
				</div>

				<div>
					<h3 className="card-description">{description}</h3>
				</div>

				<div>
					<a className="card-link">
						{link}

						<ClayIcon
							className="card-icon"
							symbol="order-arrow-right"
						/>
					</a>
				</div>
			</div>
		</div>
	);
}
