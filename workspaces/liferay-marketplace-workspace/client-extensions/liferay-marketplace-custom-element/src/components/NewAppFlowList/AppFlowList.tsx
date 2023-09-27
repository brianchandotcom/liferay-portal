/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
