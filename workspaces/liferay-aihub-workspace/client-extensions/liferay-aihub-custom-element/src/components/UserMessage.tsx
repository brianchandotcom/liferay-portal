/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

interface UserMessageProps {
	text: string;
}

export default function UserMessage({text}: UserMessageProps) {
	return (
		<div className="aihub-msg-user">
			<span className="aihub-msg-user-text">{text}</span>
		</div>
	);
}
