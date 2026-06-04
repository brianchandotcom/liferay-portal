/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import ChatbotAvatar from './ChatbotAvatar';
import Logo from './Logo';

interface ChatbotIntroProps {
	avatar?: string;
	introMessage: string;
	title: string;
}

export default function ChatbotIntro({
	avatar,
	introMessage,
	title,
}: ChatbotIntroProps) {
	return (
		<div className="aihub-intro">
			<ChatbotAvatar
				avatar={avatar}
				className="aihub-intro-logo"
				fallback={<Logo className="aihub-intro-logo" />}
				title={title}
			/>

			<div className="aihub-intro-name">{title}</div>

			<p className="aihub-intro-text">{introMessage}</p>
		</div>
	);
}
