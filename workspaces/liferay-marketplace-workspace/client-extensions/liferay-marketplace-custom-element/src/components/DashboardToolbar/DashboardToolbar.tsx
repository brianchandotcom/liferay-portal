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

import ClayButton from '@clayui/button';
import ClayManagementToolbar from '@clayui/management-toolbar';
import ClayNavigationBar from '@clayui/navigation-bar';
import classnames from 'classnames';

import liferayIcon from '../../assets/icons/liferay_icon.svg';

import './DashboardToolbar.scss';

import {useState} from 'react';

export function DashboardToolbar() {
	const [active, setActive] = useState('Dashboard');

	return (
		<div className="dashboard-toolbar-container">
			<ClayManagementToolbar.ItemList expand>
				<div className="dashboard-toolbar-title-container">
					<img
						className="dashboard-toolbar-liferay-icon"
						src={liferayIcon}
					/>

					<span className="dashboard-toolbar-title">Marketplace</span>
				</div>
			</ClayManagementToolbar.ItemList>

			<ClayManagementToolbar.ItemList>
				<ClayNavigationBar triggerLabel={active}>
					<ClayNavigationBar.Item active={active === 'Applications'}>
						<ClayButton
							className="dashboard-toolbar-navigation-bar-button"
							onClick={() => setActive('Applications')}
						>
							<span
								className={classnames(
									'dashboard-toolbar-navigation-bar-button-text',
									{
										'dashboard-toolbar-navigation-bar-button-text-active':
											active === 'Applications',
									}
								)}
							>
								Applications
							</span>
						</ClayButton>
					</ClayNavigationBar.Item>

					<ClayNavigationBar.Item active={active === 'Solutions'}>
						<ClayButton
							className="dashboard-toolbar-navigation-bar-button"
							onClick={() => setActive('Solutions')}
						>
							<span
								className={classnames(
									'dashboard-toolbar-navigation-bar-button-text',
									{
										'dashboard-toolbar-navigation-bar-button-text-active':
											active === 'Solutions',
									}
								)}
							>
								Solutions
							</span>
						</ClayButton>
					</ClayNavigationBar.Item>

					<ClayNavigationBar.Item active={active === 'Dashboard'}>
						<ClayButton
							className="dashboard-toolbar-navigation-bar-button"
							onClick={() => setActive('Dashboard')}
						>
							<span
								className={classnames(
									'dashboard-toolbar-navigation-bar-button-text',
									{
										'dashboard-toolbar-navigation-bar-button-text-active':
											active === 'Dashboard',
									}
								)}
							>
								Dashboard
							</span>
						</ClayButton>
					</ClayNavigationBar.Item>
				</ClayNavigationBar>
			</ClayManagementToolbar.ItemList>
		</div>
	);
}
