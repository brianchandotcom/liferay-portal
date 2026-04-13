/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {ErrorIcon} from './Icons';

export default function ErrorMessage() {
	return (
		<div className="aihub-msg-assistant aihub-msg-error">
			<div className="aihub-msg-assistant-icon">
				<ErrorIcon />
			</div>

			<div className="aihub-msg-assistant-text">
				<p>Sorry, an error occurred. Please try again.</p>
			</div>
		</div>
	);
}
