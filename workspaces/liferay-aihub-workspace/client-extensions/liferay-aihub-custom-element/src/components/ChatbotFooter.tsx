/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

const DEFAULT_DISCLAIMER_MESSAGE =
	'AI-generated responses can be inaccurate. Please review carefully.';

interface ChatbotFooterProps {
	disclaimerMessage: string;
}

export default function ChatbotFooter({disclaimerMessage}: ChatbotFooterProps) {
	return (
		<div className="aihub-footer">
			{disclaimerMessage || DEFAULT_DISCLAIMER_MESSAGE}
		</div>
	);
}
