/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Container as ClayContainer} from '@clayui/layout';
import classNames from 'classnames';
import {ReactNode} from 'react';

export default function Container({
	children,
	className,
}: {
	children: ReactNode;
	className?: string;
}) {
	return (
		<ClayContainer
			className={classNames(
				'bg-white border-1 rounded p-4 mt-4',
				className
			)}
		>
			{children}
		</ClayContainer>
	);
}
