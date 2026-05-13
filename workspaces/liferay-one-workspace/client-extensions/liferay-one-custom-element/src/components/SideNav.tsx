/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Fragment} from 'react';
import {NavLink} from 'react-router-dom';

export type NavItem = {
	children?: NavItem[];
	label: string;
	path: string;
	section?: string;
};

const SIDEBAR_BG = '#f4f5f8';
const TEXT_INACTIVE = '#444d6d';
const TEXT_ACTIVE = '#4a6cf7';
const ACTIVE_BG = 'rgba(74, 108, 247, 0.08)';
const TEXT_SECTION = '#8892a4';

type SideNavItemProps = {
	depth: number;
	item: NavItem;
};

function SideNavItem({depth, item}: SideNavItemProps) {
	const hasChildren = Boolean(item.children && item.children.length);

	return (
		<li>
			<NavLink
				className="d-block text-decoration-none"
				end={!hasChildren}
				style={({isActive}) => ({
					backgroundColor: isActive ? ACTIVE_BG : undefined,
					borderRadius: '6px',
					color: isActive ? TEXT_ACTIVE : TEXT_INACTIVE,
					fontWeight: isActive ? 600 : undefined,
					margin: '1px 8px',
					paddingBottom: '0.5rem',
					paddingLeft: `${0.75 + depth}rem`,
					paddingRight: '0.75rem',
					paddingTop: '0.5rem',
				})}
				to={item.path}
			>
				{item.label}
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
			className="align-self-start d-flex flex-column flex-shrink-0 overflow-auto pb-3 pt-3"
			style={{
				backgroundColor: SIDEBAR_BG,
				borderRadius: '0.75rem',
				width: '220px',
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

			<ul className="flex-fill list-unstyled m-0">
				{items.map((item, index) => {
					const prevSection =
						index > 0 ? items[index - 1].section : undefined;
					const showSectionHeader =
						item.section && item.section !== prevSection;

					return (
						<Fragment key={item.path}>
							{showSectionHeader && (
								<li
									className="font-weight-bold pb-1 px-3 text-uppercase"
									style={{
										color: TEXT_SECTION,
										fontSize: '0.625rem',
										letterSpacing: '0.1em',
										paddingTop:
											index === 0 ? '0.25rem' : '1.25rem',
									}}
								>
									{item.section}
								</li>
							)}

							<SideNavItem depth={0} item={item} />
						</Fragment>
					);
				})}
			</ul>
		</nav>
	);
}
