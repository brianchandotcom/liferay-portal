/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import Circle from '~/shared/components/Circle';

const CLASSNAME_LEGEND = 'chart-legend';
const CLASSNAME_LEGEND_ITEM = 'chart-legend-item';

export type LegendData = {
	color: string;
	name: string;
};

interface LegendIProps extends React.HTMLAttributes<HTMLElement> {
	data: Array<LegendData>;
}

const Legend: React.FC<LegendIProps> = ({data, ...otherProps}) => (
	<ul className={CLASSNAME_LEGEND} {...otherProps}>
		{data.map(({color, name}, index) => (
			<li className={CLASSNAME_LEGEND_ITEM} key={index}>
				<Circle color={color} /> {` ${name}`}
			</li>
		))}
	</ul>
);

export default Legend;
