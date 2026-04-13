/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export default function ChatbotFooter() {
	return (
		<div className="aihub-footer">
			By messaging, you agree that this chat may be monitored and recorded
			per our{' '}
			<a
				href="https://www.liferay.com/privacy-policy"
				rel="noopener noreferrer"
				target="_blank"
			>
				Privacy Policy
			</a>
		</div>
	);
}
