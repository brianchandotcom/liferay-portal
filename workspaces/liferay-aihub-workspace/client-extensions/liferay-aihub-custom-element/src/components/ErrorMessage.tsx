/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

export default function ErrorMessage() {
	return (
		<div className="aihub-msg-assistant aihub-msg-error">
			<div className="aihub-msg-assistant-icon">
				<ClayIcon symbol="exclamation-full" />
			</div>

			<div className="aihub-msg-assistant-text">
				<p>Sorry, an error occurred. Please try again.</p>
			</div>
		</div>
	);
}
