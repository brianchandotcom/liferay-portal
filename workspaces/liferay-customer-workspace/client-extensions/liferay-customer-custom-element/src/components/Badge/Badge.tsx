/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

interface IProps {
	alertType?: 'danger' | 'info' | 'success' | 'warning';
	badgeClassName?: string;
	children?: any;
}

const Badge = ({
	alertType = 'danger',
	badgeClassName,
	children,
	...props
}: IProps) => {
	return (
		<div
			{...props}
			className={classNames(
				'alert',
				'alert-' + alertType,
				'p-sm-2',
				'text-' + alertType,
				'text-paragraph-sm',
				{
					[badgeClassName as string]: badgeClassName,
				}
			)}
		>
			<ClayIcon symbol="exclamation-full" />

			{children}
		</div>
	);
};

export default Badge;
