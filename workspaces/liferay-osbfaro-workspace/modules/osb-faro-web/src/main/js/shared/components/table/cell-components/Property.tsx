/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';
import {getSafeDisplayValue} from '~/shared/util/util';

interface IPropertyCellProps {
	className?: string;
	data: {
		name: string;
		value: string;
	};
}

const PropertyCell: React.FC<IPropertyCellProps> = ({
	className,
	data: {name, value},
}) => (
	<td className={getCN('property-cell', className)}>
		<div className="name">{name}</div>

		<div className="table-title">{getSafeDisplayValue(value)}</div>
	</td>
);

export default PropertyCell;
