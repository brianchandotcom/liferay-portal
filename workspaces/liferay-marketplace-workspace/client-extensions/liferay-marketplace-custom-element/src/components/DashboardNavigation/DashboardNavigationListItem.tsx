/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';

import circleFill from '../../assets/icons/circle_fill_icon.svg';

import './DashboardNavigationListItem.scss';
import {getThumbnailByProductAttachment, showAppImage} from '../../utils/util';
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
	const {attachments, name, selected, status, version} = item;
	const thumbnail = getThumbnailByProductAttachment(attachments);

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
