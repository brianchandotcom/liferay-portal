/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React from 'react';

interface ITrendProps extends React.HTMLAttributes<HTMLDivElement> {
	color: string;
	icon?: string;
	label: string;
}

const Trend: React.FC<ITrendProps> = ({className, color, icon, label}) => (
	<div className={getCN('analytics-trend', className)} style={{color}}>
		{icon && <ClayIcon className="icon-root" symbol={icon} />}
		<span className="analytics-trend-percent mb-0">{label}</span>
	</div>
);

export default Trend;
