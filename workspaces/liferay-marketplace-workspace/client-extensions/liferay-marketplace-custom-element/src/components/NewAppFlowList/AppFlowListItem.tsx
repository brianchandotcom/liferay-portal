/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

import './AppFlowListItem.scss';

import classNames from 'classnames';

interface AppFlowListItemProps {
	checked?: boolean;
	selected?: boolean;
	text: string;
}

export function AppFlowListItem({
	checked = false,
	selected = false,
	text,
}: AppFlowListItemProps) {
	const getIcon = () => {
		if (checked) {
			return 'check';
		}

		if (selected) {
			return 'radio-button';
		}

		return 'circle';
	};

	return (
		<div className="app-flow-list-item-container">
			<ClayIcon
				aria-label={
					'check fill' ?? selected ? 'radio selected' : 'circle fill'
				}
				className={classNames('app-flow-list-item-icon text-muted', {
					'app-flow-list-item-icon-checked': checked,
					'app-flow-list-item-icon-selected': selected,
				})}
				symbol={getIcon()}
			/>

			<li
				className={classNames('app-flow-list-item-text', {
					'app-flow-list-item-text-checked': checked || selected,
					[`list-item-selected-${text.toLowerCase()}`]:
						checked || selected,
				})}
			>
				{text}
			</li>
		</div>
	);
}
