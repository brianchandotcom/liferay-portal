/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import ReactMarkdown from 'react-markdown';

import ChatbotAvatar from './ChatbotAvatar';
import {StarsIcon} from './Icons';

interface AssistantMessageProps {
	avatar?: string;
	text: string;
	title: string;
}

export default function AssistantMessage({
	avatar,
	text,
	title,
}: AssistantMessageProps) {
	return (
		<div className="aihub-msg-assistant">
			<div className="aihub-msg-assistant-icon">
				<ChatbotAvatar
					avatar={avatar}
					className="aihub-msg-assistant-company-logo"
					fallback={<StarsIcon />}
					title={title}
				/>
			</div>

			<div className="aihub-msg-assistant-text">
				<ReactMarkdown>{text}</ReactMarkdown>
			</div>
		</div>
	);
}
