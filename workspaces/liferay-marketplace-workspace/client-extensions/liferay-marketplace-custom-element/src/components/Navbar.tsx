/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {NavLink, useLocation} from 'react-router-dom';

export type NavbarProps = {
	routes: {
		name: string;
		path: string;
		visible?: boolean;
	}[];
};

const Navbar: React.FC<NavbarProps> = ({routes}) => {
	const location = useLocation();

	const routeParams = location.pathname.split('/').filter(Boolean);

	return (
		<div className="navbar navbar-expand-md navbar-underline navigation-bar navigation-bar-light">
			<ul className="navbar-nav">
				{routes
					.filter(({visible = true}) => visible)
					.map((route, index) => (
						<NavLink
							className={({isActive}) =>
								classNames('nav-link', {
									active:
										index === 0
											? isActive &&
												routeParams.length === 2
											: isActive,
								})
							}
							key={index}
							to={route.path}
						>
							{route.name}
						</NavLink>
					))}
			</ul>
		</div>
	);
};

export default Navbar;
