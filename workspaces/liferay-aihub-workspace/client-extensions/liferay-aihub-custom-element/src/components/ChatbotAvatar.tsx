/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

interface ChatbotAvatarProps {
	avatar?: string;
	className?: string;
	fallback: React.ReactNode;
	title: string;
}

export default function ChatbotAvatar({
	avatar,
	className,
	fallback,
	title,
}: ChatbotAvatarProps) {
	if (avatar) {
		return (
			<div className={className}>
				<img alt={title} src={avatar} />
			</div>
		);
	}

	return <>{fallback}</>;
}
