/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
interface IProps {
	children?: React.ReactNode;
}

const Footer = ({
	children,
	className,
}: IProps & React.HTMLAttributes<HTMLDivElement>) => (
	<div
		className={classNames(
			'border-neutral-2 border-top d-md-flex mt-4 pt-4',
			className
		)}
	>
		{children}
	</div>
);

export default Footer;
