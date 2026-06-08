/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';

export const SummaryTitle = function SummaryTitle({
	className,
	label,
}: {
	className?: string;
	label: React.ReactNode;
}) {
	return <h3 className={getCN('font-weight-bold', className)}>{label}</h3>;
};
