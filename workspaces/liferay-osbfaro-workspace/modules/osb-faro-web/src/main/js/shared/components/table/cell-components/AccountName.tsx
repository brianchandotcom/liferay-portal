/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';

interface IAccountNamesProps {
	className?: string;
	data: {
		accountName: string;
	};
}

const AccountNames: React.FC<IAccountNamesProps> = ({
	className,
	data: {accountName},
}) => (
	<td className={getCN('name-cell-root', className)}>
		<div className="text-truncate">{accountName || '-'}</div>
	</td>
);

export default AccountNames;
