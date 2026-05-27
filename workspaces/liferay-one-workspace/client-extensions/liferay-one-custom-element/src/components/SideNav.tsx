/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {NavLink} from 'react-router-dom';

import unionIconUrl from '../assets/icons/union.svg';

export type NavItem = {
	children?: NavItem[];
	icon?: string;
	label: string;
	path: string;
};

const ACTIVE_BG = '#E6EDFB';
const CUSTOM_ICONS = ['union'] as const;
const SIDEBAR_BG = '#F7F7F8';
const TEXT_ACTIVE = '#004AD7';
const TEXT_INACTIVE = '#282934';
const TEXT_SECTION = '#6B6C7E';

type SideNavItemProps = {
	depth: number;
	item: NavItem;
};

function SideNavItem({depth, item}: SideNavItemProps) {
	const hasChildren = Boolean(item.children && item.children.length);
	const icon = item.icon;
	const isCustomIcon =
		!!icon && CUSTOM_ICONS.includes(icon as (typeof CUSTOM_ICONS)[number]);

	return (
		<li>
			<NavLink
				className="align-items-center d-flex text-decoration-none w-100"
				end={!hasChildren}
				style={({isActive}) => ({
					backgroundColor: isActive ? ACTIVE_BG : 'transparent',
					borderRadius: '0.375rem',
					color: isActive ? TEXT_ACTIVE : TEXT_INACTIVE,
					fontSize: '1rem',
					fontWeight: 600,
					gap: '0.75rem',
					lineHeight: '1.5rem',
					padding: `0.75rem 0.75rem 0.75rem ${0.75 + depth * 0.75}rem`,
				})}
				to={item.path}
			>
				{({isActive}) => (
					<>
						{icon && (
							<ClayIcon
								spritemap={
									isCustomIcon ? unionIconUrl : undefined
								}
								style={{
									flexShrink: 0,
									opacity: isActive ? 0.8 : 0.5,
								}}
								symbol={icon}
							/>
						)}

						<span style={{flex: '1 0 0', textAlign: 'left'}}>
							{item.label}
						</span>
					</>
				)}
			</NavLink>

			{hasChildren && (
				<ul className="list-unstyled m-0">
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
		<nav
			className="align-self-start d-flex flex-column flex-shrink-0 overflow-auto"
			style={{
				backgroundColor: SIDEBAR_BG,
				borderRadius: '0.625rem',
				padding: '0.5rem',
				width: '18rem',
			}}
		>
			{title && (
				<div
					className="font-weight-bold pb-2 px-3 text-uppercase"
					style={{
						color: TEXT_SECTION,
						fontSize: '0.625rem',
						letterSpacing: '0.1em',
					}}
				>
					{title}
				</div>
			)}

			<ul className="d-flex flex-column flex-fill gap-2 list-unstyled m-0">
				{items.map((item) => (
					<SideNavItem depth={0} item={item} key={item.path} />
				))}
			</ul>
		</nav>
	);
}
