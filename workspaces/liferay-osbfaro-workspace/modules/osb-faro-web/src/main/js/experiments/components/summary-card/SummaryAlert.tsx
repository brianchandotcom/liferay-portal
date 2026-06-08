/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

interface SummaryAlertIProps extends React.HTMLAttributes<HTMLElement> {
	symbol?: string;
}

export const SummaryAlert = function SummaryAlert({
	children,
	symbol,
}: SummaryAlertIProps) {
	return (
		<div className="analytics-summary-card-alert p-4 w-100">
			{symbol ? (
				<>
					<ClayIcon symbol={symbol} />

					<div>{children}</div>
				</>
			) : (
				children
			)}
		</div>
	);
};
