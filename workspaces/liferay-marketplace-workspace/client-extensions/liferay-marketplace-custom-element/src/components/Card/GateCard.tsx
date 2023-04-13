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

				{link && (
					<ClayLink className="card-link" href={link.href}>
						{link.label}

						<ClayIcon
							className="card-icon"
							symbol="order-arrow-right"
						/>
					</ClayLink>
				)}
			</div>
		</div>
	);
}
