/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {ComponentProps} from 'react';

import i18n from '../../../i18n';
import {getAccountImage} from '../../../utils/util';

import './index.scss';

type NavbarProps = {
	accountImage?: string;
	accountName: string;
	appImage?: string;
	appName?: string;
	display?: {preview?: boolean; saveAsDraft?: boolean; submit?: boolean};
	exitHref?: string;
	exitProps?: ComponentProps<typeof ClayButton>;
	previewProps?: Omit<
		ComponentProps<typeof ClayButtonWithIcon>,
		'aria-label' | 'symbol'
	>;
	saveAsDraftProps?: ComponentProps<typeof ClayButton>;
	submitProps?: ComponentProps<typeof ClayButton>;
};

const Navbar: React.FC<NavbarProps> = ({
	accountImage,
	accountName,
	appImage,
	appName,
	display = {preview: false, saveAsDraft: false, submit: false},
	exitProps,
	saveAsDraftProps,
	submitProps,
}) => (
	<>
		<div className="new-app-tool-bar-container pb-4">
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
								{appName || 'New App'}
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
				<div className="align-items-center d-flex flex-row gap-3 justify-content-between mkt-gap-1">
					<ClayButton
						className="new-app-tool-bar-button-text text-dark"
						displayType="unstyled"
						{...exitProps}
					>
						{i18n.translate('exit')}
					</ClayButton>

					{display.saveAsDraft && (
						<ClayButton
							className="text-dark"
							displayType="secondary"
							size="xs"
							{...saveAsDraftProps}
						>
							{i18n.translate('save-as-draft')}
						</ClayButton>
					)}

					{display.submit && (
						<ClayButton
							className="text-dark"
							displayType="secondary"
							size="xs"
							{...submitProps}
						>
							{i18n.translate('submit')}
						</ClayButton>
					)}
				</div>
			</ClayManagementToolbar.ItemList>
		</div>

		<hr />
	</>
);

export default Navbar;
