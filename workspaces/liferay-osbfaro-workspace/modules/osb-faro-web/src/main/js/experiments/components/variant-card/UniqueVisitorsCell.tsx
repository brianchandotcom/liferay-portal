/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {toRounded, toThousands} from '~/shared/util/numbers';

interface UniqueVisitorsCellIProps
	extends React.TdHTMLAttributes<HTMLTableCellElement> {
	trafficSplit: number;
	uniqueVisitors: number;
}

const UniqueVisitorsCell: React.FC<UniqueVisitorsCellIProps> = ({
	trafficSplit,
	uniqueVisitors,
	...otherProps
}) => (
	<td {...otherProps}>
		<div className="d-flex flex-column w-100">
			<span className="unique-visitors">
				{toThousands(uniqueVisitors)}
			</span>
			<span className="traffic-quota">
				{`${toRounded(trafficSplit)}% ${Liferay.Language.get(
					'traffic-split'
				)}`}
			</span>
		</div>
	</td>
);

export default UniqueVisitorsCell;
