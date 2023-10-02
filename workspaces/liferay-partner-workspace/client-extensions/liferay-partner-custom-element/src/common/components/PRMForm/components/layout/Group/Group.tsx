/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {Children} from 'react';

interface IProps {
	children?: React.ReactNode;
}

const Group = ({
	children,
	className,
}: IProps & React.HTMLAttributes<HTMLDivElement>) => (
	<div className={classNames('form-group-autofit mb-0', className)}>
		{Children.map(children, (child) => {
			return <div className="form-group-item">{child}</div>;
		})}
	</div>
);

export default Group;
