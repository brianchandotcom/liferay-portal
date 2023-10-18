/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';

import circleFill from '../../assets/icons/circle_fill_icon.svg';

import './DashboardNavigationListItem.scss';

import {NavLink} from 'react-router-dom';

import {getThumbnailByProductAttachment, showAppImage} from '../../utils/util';
import {AppProps} from '../DashboardTable/DashboardTable';
interface DashboardNavigationListItem {
	item: AppProps;
}

export function DashboardNavigationListItem({
	item,
}: DashboardNavigationListItem) {
	const {attachments, name, status, version} = item;
	const thumbnail = getThumbnailByProductAttachment(attachments);

	return (
		<NavLink
			className={({isActive}) =>
				classNames('dashboard-navigation-body-list-item', {
					'dashboard-navigation-body-list-item-selected': isActive,
				})
			}
			to={`/app/${item.productId}`}
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
		</NavLink>
	);
}
