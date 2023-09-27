/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';

import './AccountHeaderButton.scss';

interface AccountHeaderButtonProps {
	boldText: string;
	name: string;
	onClick?: (value: string) => void;
	text: string;
	title: string;
}

export function AccountHeaderButton({
	boldText,
	name,
	onClick,
	text,
	title,
}: AccountHeaderButtonProps) {
	return (
		<div className="account-details-header-right-content-container">
			<span className="account-details-header-right-content-title">
				{title}
			</span>

			<ClayButton
				displayType="unstyled"
				onClick={() => onClick && onClick(name)}
			>
				<div className="account-details-header-right-content-button-container">
					<strong className="account-details-header-right-content-button-text-bold">
						{boldText}
					</strong>

					<span className="account-details-header-right-content-button-text">
						{text}
					</span>

					<ClayIcon symbol="angle-right-small" />
				</div>
			</ClayButton>
		</div>
	);
}
