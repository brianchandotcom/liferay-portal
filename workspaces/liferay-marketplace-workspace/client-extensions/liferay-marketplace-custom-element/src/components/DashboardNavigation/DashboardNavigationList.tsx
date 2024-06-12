/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';

import './DashboardNavigationList.scss';

import ClayIcon from '@clayui/icon';
import {NavLink, useLocation} from 'react-router-dom';

import {DashboardListItems} from './DashboardNavigation';
import {DashboardNavigationListItem} from './DashboardNavigationListItem';

type DashboardNavigationListProps = {
	dashboardNavigation: DashboardListItems;
};

export function DashboardNavigationList({
	dashboardNavigation,
}: DashboardNavigationListProps) {
	const {itemTitle, items, path, symbol} = dashboardNavigation;

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
				to={path}
			>
				{({isActive}) => (
					<>
						<span
							className={classNames(
								'dashboard-navigation-body-list-icon',
								{
									'dashboard-navigation-body-list-icon-selected':
										isActive,
								}
							)}
						>
							<ClayIcon symbol={symbol as string} />
						</span>

						<span
							className={classNames(
								'dashboard-navigation-body-list-text',
								{
									'dashboard-navigation-body-list-text-selected':
										isActive,
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
