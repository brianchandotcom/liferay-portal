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

import {AppFlowListItem} from './AppFlowListItem';

import './AppFlowList.scss';
import {AppFlowListItemProps} from '../../pages/AppCreationFlow/AppCreationFlowUtil';

interface AppFlowListProps {
	appFlowListItems: AppFlowListItemProps[];
}

export function AppFlowList({appFlowListItems}: AppFlowListProps) {
	return (
		<div className="app-flow-list-container">
			<ul className="app-flow-list-ul">
				{appFlowListItems.map((appItem) => (
					<AppFlowListItem
						checked={appItem.checked}
						key={appItem.name}
						selected={appItem.selected}
						text={appItem.label}
					/>
				))}
			</ul>
		</div>
	);
}
