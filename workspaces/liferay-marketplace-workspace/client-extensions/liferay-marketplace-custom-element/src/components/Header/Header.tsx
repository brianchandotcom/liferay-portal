import './Header.scss';

interface HeaderProps {
	description: string;
	title: string;
}

export function Header({description, title}: HeaderProps) {
	return (
		<div className="header-container">
			<span className="header-title">{title}</span>

			<p className="header-description">{description}</p>
		</div>
	);
}
