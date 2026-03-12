/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {HTMLAttributes} from 'react';

import './AlertBox.scss';

export default function AlertBox({
	className,
	...props
}: HTMLAttributes<HTMLDivElement>) {
	return (
		<div
			{...props}
			className={classNames('alert-box-container', className)}
		>
			<ClayIcon
				color="#B95000"
				fontSize={32}
				symbol="warning-full
"
			/>
		</div>
	);
}
