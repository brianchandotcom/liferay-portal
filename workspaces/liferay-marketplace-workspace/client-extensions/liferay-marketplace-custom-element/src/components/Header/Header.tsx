import {ReactNode} from 'react';

import './Header.scss';

interface HeaderProps {
	description: ReactNode | string;
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
