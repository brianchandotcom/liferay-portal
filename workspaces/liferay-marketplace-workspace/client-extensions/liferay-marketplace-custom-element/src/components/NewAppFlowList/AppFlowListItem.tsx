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
