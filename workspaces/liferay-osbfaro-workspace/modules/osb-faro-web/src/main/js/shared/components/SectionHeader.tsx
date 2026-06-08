/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import React from 'react';

interface ISectionHeader {
	className?: string;
	icon: string;
	title: string;
}

const SectionHeader: React.FC<ISectionHeader> = ({
	className = 'mb-3',
	icon,
	title,
}) => (
	<div className={className}>
		<span className="mr-2">
			<Text color="secondary" size={4}>
				<ClayIcon symbol={icon} />
			</Text>
		</span>

		<Text color="secondary" size={4} weight="semi-bold">
			{title.toUpperCase()}
		</Text>
	</div>
);

export {SectionHeader};
