/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import classNames from 'classnames';

import circleFill from '../../assets/icons/circle_fill_icon.svg';

import './DashboardNavigationListItem.scss';
import {showAppImage} from '../../utils/util';
import {AppProps} from '../DashboardTable/DashboardTable';
import {DashboardListItems} from './DashboardNavigation';
interface DashboardNavigationListItem {
	dashboardNavigationItems: DashboardListItems[];
	item: AppProps;
	items: AppProps[];
	listName: string;
	onSelectAppChange?: (value: AppProps) => void;
	setDashboardNavigationItems: (values: DashboardListItems[]) => void;
}

export function DashboardNavigationListItem({
	dashboardNavigationItems,
	item,
	items,
	listName,
	onSelectAppChange,
	setDashboardNavigationItems,
}: DashboardNavigationListItem) {
	const {name, selected, status, thumbnail, version} = item;

	return (
		<div
			className={classNames('dashboard-navigation-body-list-item', {
				'dashboard-navigation-body-list-item-selected': selected,
			})}
			onClick={() => {
				const newItems = items.map((item) => {
					if (item.name === name) {
						return {
							...item,
							selected: !item.selected,
						};
					}

					return {
						...item,
						selected: false,
					};
				});

				const newDashboardNavigationItems = dashboardNavigationItems.map(
					(navigationItem) => {
						if (navigationItem.itemName === listName) {
							return {
								...navigationItem,
								items: newItems,
							};
						}

						return navigationItem;
					}
				);

				setDashboardNavigationItems(newDashboardNavigationItems);

				if (onSelectAppChange) {
					onSelectAppChange(item);
				}
			}}
		>
			<div>
				<img
					alt="App Image"
					className="dashboard-navigation-body-list-item-app-logo"
					src={showAppImage(thumbnail)}
				/>

				<span className="dashboard-navigation-body-list-item-app-title">
					{name}
				</span>

				<span className="dashboard-navigation-body-list-item-app-version">
					{version}
				</span>
			</div>

			<img
				alt="Circle fill"
				className={classNames(
					'dashboard-navigation-body-list-item-app-status',
					{
						'dashboard-navigation-body-list-item-app-status-hidden':
							status === 'Hidden',
						'dashboard-navigation-body-list-item-app-status-pending':
							status === 'Pending',
						'dashboard-navigation-body-list-item-app-status-published':
							status === 'Published',
					}
				)}
				src={circleFill}
			/>
		</div>
	);
}
