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
