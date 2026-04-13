/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {CloseIcon} from './Icons';
import Logo from './Logo';

interface ChatbotHeaderProps {
	onClose: () => void;
	title: string;
}

export default function ChatbotHeader({onClose, title}: ChatbotHeaderProps) {
	return (
		<div className="aihub-header">
			<Logo className="aihub-header-logo" />

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
