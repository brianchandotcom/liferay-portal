/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import React from 'react';

interface IUsageMetricProps {
	children?: React.ReactNode;
	description: string;
	title: string;
}

export const UsageMetric = function UsageMetric({
	children,
	description,
	title,
}: IUsageMetricProps) {
	return (
		<>
			<Text color="secondary" size={3} weight="semi-bold">
				{title.toUpperCase()}
			</Text>
			<hr className="my-2" />
			<Text color="secondary" size={3}>
				{description}
			</Text>
			<div className="mt-3">{children}</div>
		</>
	);
};
