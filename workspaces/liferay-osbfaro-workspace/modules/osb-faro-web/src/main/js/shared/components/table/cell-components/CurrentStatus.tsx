/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

interface ICurrentStatusProps {
	className?: string;
	data: {
		currentMember: boolean;
	};
}

const CurrentStatus: React.FC<ICurrentStatusProps> = ({
	className,
	data: {currentMember},
}) => (
	<td className={className}>
		{currentMember
			? Liferay.Language.get('member')
			: Liferay.Language.get('non-member')}
	</td>
);

export default CurrentStatus;
