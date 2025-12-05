/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DashboardNavigationItem from './DashboardNavigationItem';

import './DashboardNavigation.scss';

export type DashboardListItems = {
	active?: boolean;
	itemTitle: string;
	path: string;
	symbol: string;
	visible?: boolean;
};

export type DashboardNavigationProps = {
	dashboardNavigationItems: DashboardListItems[];
};

export function DashboardNavigation({
	dashboardNavigationItems,
}: DashboardNavigationProps) {
	return (
		<div className="dashboard-navigation-container">
			<div className="dashboard-navigation-body dashboard-navigation-container-dropdown">
				{dashboardNavigationItems.map((dashboardNavigation, index) => (
					<DashboardNavigationItem
						dashboardNavigation={dashboardNavigation}
						key={index}
					/>
				))}
			</div>
		</div>
	);
}
