/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import checkFill from '../../assets/icons/check_fill_icon.svg';
import circleFill from '../../assets/icons/circle_fill_icon.svg';
import radioSelected from '../../assets/icons/radio_button_checked_2_icon.svg';

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
			return checkFill;
		}

		if (selected) {
			return radioSelected;
		}

		return circleFill;
	};

	return (
		<div className="app-flow-list-item-container">
			<img
				alt={
					checkFill
						? 'check fill'
						: selected
						? 'radio selected'
						: 'circle fill'
				}
				className={classNames('app-flow-list-item-icon', {
					'app-flow-list-item-icon-checked': checked,
					'app-flow-list-item-icon-selected': selected,
				})}
				src={getIcon()}
			/>

			<li
				className={classNames('app-flow-list-item-text', {
					'app-flow-list-item-text-checked': checked || selected,
				})}
			>
				{text}
			</li>
		</div>
	);
}
