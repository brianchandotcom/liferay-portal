/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayManagementToolbar from '@clayui/management-toolbar';

import chevronRight from '../../assets/icons/chevron_right_icon.svg';
import circleFill from '../../assets/icons/circle_fill_icon.svg';
import emptyPicture from '../../assets/icons/empty_picture_icon.svg';
import {getAccountImage} from '../../utils/util';

import './NewAppToolBar.scss';

import {Link} from 'react-router-dom';

type NewAppToolBarProps = {
	accountImage?: string;
	accountName: string;
	appImage?: string;
	appName?: string;
};

export function NewAppToolBar({
	accountImage,
	accountName,
	appImage,
	appName,
}: NewAppToolBarProps) {
	return (
		<div className="container new-app-tool-bar-container">
			<ClayManagementToolbar.ItemList expand>
				<div className="d-flex justify-content-between">
					<div className="d-flex">
						<div className="new-app-tool-bar-main-account-logo">
							<img
								alt="Main account logo"
								className="new-app-tool-bar-main-account-logo-img"
								src={getAccountImage(accountImage)}
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
					</div>

					<div className="flex-shrink-0 new-app-tool-bar-status-container">
						<img
							alt="Status"
							className="new-app-tool-bar-status-icon"
							src={circleFill}
						/>

						<span className="new-app-tool-bar-status-text">
							Draft
						</span>
					</div>
				</div>
			</ClayManagementToolbar.ItemList>

			<ClayManagementToolbar.ItemList>
				<ClayButton.Group className="new-app-tool-bar-button-container">
					<Link to="../">
						<ClayButton
							className="new-app-tool-bar-button-exit"
							displayType={null}
						>
							<span className="new-app-tool-bar-button-text">
								Exit
							</span>
						</ClayButton>
					</Link>
				</ClayButton.Group>
			</ClayManagementToolbar.ItemList>
		</div>
	);
}
