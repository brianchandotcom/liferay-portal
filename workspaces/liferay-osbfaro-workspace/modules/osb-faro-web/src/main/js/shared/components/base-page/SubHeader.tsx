/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';

import Row from './Row';

interface ISubHeaderProps extends React.HTMLAttributes<HTMLDivElement> {
	fluid?: boolean;
}

const SubHeader: React.FC<ISubHeaderProps> = ({
	children,
	className,
	fluid = false,
}) => {
	if (fluid) {
		return (
			<div className={getCN('sub-header-root', className)}>
				<div className="mx-5">{children}</div>
			</div>
		);
	}

	return (
		<div className={getCN('sub-header-root', className)}>
			<Row className="header-container">{children}</Row>
		</div>
	);
};

export default SubHeader;
