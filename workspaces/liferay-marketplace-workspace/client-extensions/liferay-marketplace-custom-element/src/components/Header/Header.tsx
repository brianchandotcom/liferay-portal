/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

import './Header.scss';

type HeaderProps = {
	description?: ReactNode | string;
	title?: ReactNode | string;
};

export function Header({description, title}: HeaderProps) {
	return (
		<div className="header-container">
			<span className="header-title">{title}</span>

			<div className="header-description">{description}</div>
		</div>
	);
}
