/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export default function LoadingIndicator() {
	return (
		<div className="aihub-loading">
			<div className="aihub-loading-spinner" />

			<span className="aihub-loading-text">Generating&hellip;</span>
		</div>
	);
}
