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
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayManagementToolbar from '@clayui/management-toolbar';

import chevronRight from '../../assets/icons/chevron_right_icon.svg';
import circleFill from '../../assets/icons/circle_fill_icon.svg';
import dotsIcon from '../../assets/icons/dots_icon.svg';
import emptyPicture from '../../assets/icons/empty_picture_icon.svg';
import {showAccountImage} from '../../utils/util';

import './NewAppToolBar.scss';

interface NewAppToolBarProps {
	accountImage?: string;
	accountName: string;
	appImage?: string;
	appName?: string;
	enableDropdown?: boolean;
}

export function NewAppToolBar({
	accountImage,
	accountName,
	appImage,
	appName,
	enableDropdown,
}: NewAppToolBarProps) {
	type Item = {
		disabled?: boolean;
		label?: string;
		type?:
			| 'checkbox'
			| 'contextual'
			| 'group'
			| 'item'
			| 'radio'
			| 'radiogroup'
			| 'divider';
	};

	const items: Item[] = [
		{
			disabled: true,
			label: 'Publish app',
		},
		{
			disabled: true,
			label: 'Hide app',
		},
		{
			label: 'Menu List Text',
		},
		{
			type: 'divider',
		},
		{
			label: 'Remove app',
		},
	];

	return (
		<div className="container new-app-tool-bar-container">
			<ClayManagementToolbar.ItemList expand>
				<div className="new-app-tool-bar-main-account-logo">
					<img
						alt="Main account logo"
						className="new-app-tool-bar-main-account-logo-img"
						src={showAccountImage(accountImage)}
					/>

					<span className="new-app-tool-bar-main-account-logo-text">
						{accountName}
					</span>
				</div>

				<img
					alt="Arrow right"
					className="new-app-tool-bar-arrow-right"
					src={chevronRight}
				/>

				<div className="new-app-tool-bar-new-app-logo">
					<img
						alt="New App logo"
						className="new-app-tool-bar-new-app-logo-img"
						src={appImage ?? emptyPicture}
					/>

					<span className="new-app-tool-bar-new-app-logo-text">
						{appName ?? 'New App'}
					</span>
				</div>
			</ClayManagementToolbar.ItemList>

			<ClayManagementToolbar.ItemList expand>
				<div className="new-app-tool-bar-status-container">
					<img
						alt="Status"
						className="new-app-tool-bar-status-icon"
						src={circleFill}
					/>

					<span className="new-app-tool-bar-status-text">Draft</span>
				</div>
			</ClayManagementToolbar.ItemList>

			<ClayManagementToolbar.ItemList>
				<ClayButton.Group className="new-app-tool-bar-button-container">
					<a href="/dashboard">
						<ClayButton
							className="new-app-tool-bar-button-exit"
							displayType={null}
						>
							<span className="new-app-tool-bar-button-text">
								Exit
							</span>
						</ClayButton>
					</a>

					<button className="new-app-tool-bar-button-save-draft">
						Save as draft
					</button>

					<button className="new-app-tool-bar-button-preview-storefront">
						Preview Storefront
					</button>
				</ClayButton.Group>

				{enableDropdown && (
					<div className="new-app-tool-bar-button-dropdown">
						<ClayDropDownWithItems
							items={items}
							trigger={
								<ClayButton displayType={null}>
									<img
										alt="Icon"
										className="new-app-tool-bar-button-dropdown-icon"
										src={dotsIcon}
									/>
								</ClayButton>
							}
						/>
					</div>
				)}
			</ClayManagementToolbar.ItemList>
		</div>
	);
}
