/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import ChatbotAvatar from './ChatbotAvatar';
import {CloseIcon} from './Icons';
import Logo from './Logo';

interface ChatbotHeaderProps {
	avatar?: string;
	onClose: () => void;
	title: string;
}

export default function ChatbotHeader({
	avatar,
	onClose,
	title,
}: ChatbotHeaderProps) {
	return (
		<div className="aihub-header">
			<ChatbotAvatar
				avatar={avatar}
				className="aihub-header-logo"
				fallback={<Logo className="aihub-header-logo" />}
				title={title}
			/>

			<div className="aihub-header-info">
				<div className="aihub-header-title">{title}</div>
			</div>

			<button
				aria-label="Close"
				className="aihub-header-close"
				onClick={onClose}
			>
				<CloseIcon />
			</button>
		</div>
	);
}
