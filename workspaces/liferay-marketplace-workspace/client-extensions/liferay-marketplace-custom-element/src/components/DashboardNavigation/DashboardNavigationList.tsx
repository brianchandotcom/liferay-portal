/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';

import './DashboardNavigationList.scss';

import {NavLink, useLocation} from 'react-router-dom';

import {DashboardListItems} from './DashboardNavigation';
import {DashboardNavigationListItem} from './DashboardNavigationListItem';

interface DashboardNavigationListProps {
	accountId: string;
	navigationItemMock: DashboardListItems;
}

export function DashboardNavigationList({
	accountId,
	navigationItemMock,
}: DashboardNavigationListProps) {
	const {itemIcon, itemTitle, items, path} = navigationItemMock;

	const location = useLocation();

	const isAppRoute =
		location.pathname === '/' || location.pathname.includes('/app');

	return (
		<>
			<NavLink
				className={({isActive}) =>
					classNames('dashboard-navigation-body-list', {
						'dashboard-navigation-body-list-selected':
							isActive || (path === '/' && isAppRoute),
					})
				}
				to={`${path}${accountId ? `?accountId=${accountId}` : ''}`}
			>
				{({isActive}) => (
					<>
						<img
							alt="Apps icon"
							className={classNames(
								'dashboard-navigation-body-list-icon',
								{
									'dashboard-navigation-body-list-icon-selected': isActive,
								}
							)}
							src={itemIcon}
						/>

						<span
							className={classNames(
								'dashboard-navigation-body-list-text',
								{
									'dashboard-navigation-body-list-text-selected': isActive,
								}
							)}
						>
							{itemTitle}
						</span>
					</>
				)}
			</NavLink>

			{isAppRoute &&
				items?.map((item, index) => (
					<DashboardNavigationListItem item={item} key={index} />
				))}
		</>
	);
}
