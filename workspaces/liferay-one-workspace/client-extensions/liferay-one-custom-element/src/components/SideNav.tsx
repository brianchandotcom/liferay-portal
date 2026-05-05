/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {NavLink} from 'react-router-dom';

import type {CSSProperties} from 'react';

export type NavItem = {
	children?: NavItem[];
	label: string;
	path: string;
};

type SideNavItemProps = {
	depth: number;
	item: NavItem;
};

function SideNavItem({depth, item}: SideNavItemProps) {
	const hasChildren = Boolean(item.children && item.children.length);

	return (
		<li>
			<NavLink
				className={({isActive}) =>
					`side-nav__item-link${isActive ? ' side-nav__item-link--active' : ''}`
				}
				end={!hasChildren}
				style={{'--nav-depth': depth + 1} as CSSProperties}
				to={item.path}
			>
				{item.label}
			</NavLink>

			{hasChildren && (
				<ul className="side-nav__list">
					{item.children?.map((child) => (
						<SideNavItem
							depth={depth + 1}
							item={child}
							key={child.path}
						/>
					))}
				</ul>
			)}
		</li>
	);
}

type SideNavProps = {
	items: NavItem[];
	title?: string;
};

export default function SideNav({items, title}: SideNavProps) {
	return (
		<nav className="side-nav">
			{title && <div className="side-nav__title">{title}</div>}

			<ul className="side-nav__list">
				{items.map((item) => (
					<SideNavItem depth={0} item={item} key={item.path} />
				))}
			</ul>
		</nav>
	);
}
