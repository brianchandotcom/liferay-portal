/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayManagementToolbar from '@clayui/management-toolbar';

import {getAccountImage} from '../../utils/util';

import './AppToolBar.scss';

import ClayIcon from '@clayui/icon';
import {Link} from 'react-router-dom';

type AppToolBarProps = {
	accountImage?: string;
	accountName: string;
	appImage?: string;
	appName?: string;
};

export function AppToolBar({
	accountImage,
	accountName,
	appImage,
	appName,
}: AppToolBarProps) {
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

						<ClayIcon
							aria-label="Arrow right"
							className="new-app-tool-bar-arrow-right"
							symbol="angle-right"
						/>

						<div className="new-app-tool-bar-new-app-logo">
							{appImage ? (
								<img
									alt="New App logo"
									className="new-app-tool-bar-new-app-logo-img"
									src={appImage}
								/>
							) : (
								<div className="bg-light px-5 py-3 rounded">
									<ClayIcon
										aria-label="New App logo"
										className="text-muted"
										symbol="picture"
									/>
								</div>
							)}

							<span className="new-app-tool-bar-new-app-logo-text">
								{appName ?? 'New App'}
							</span>
						</div>
					</div>

					<div className="flex-shrink-0 new-app-tool-bar-status-container">
						<ClayIcon
							aria-label="Status"
							className="new-app-tool-bar-status-icon"
							symbol="circle"
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
