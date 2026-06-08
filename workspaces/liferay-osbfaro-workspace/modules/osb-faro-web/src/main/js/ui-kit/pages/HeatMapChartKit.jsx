/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {flatten, range} from 'lodash';
import React from 'react';
import HeatmapChart from '~/shared/components/HeatmapChart';

import Row from '../components/Row';

const WEEKDAYS = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];

const mockData = flatten(
	WEEKDAYS.map((day) =>
		range(24).map((i) => ({
			column: day,
			row: i,
			value: i >= 4 && i + 1 <= 20 ? Math.random() * 100 : 0,
		}))
	)
);

export default class HeatmapChartKit extends React.Component {
	render() {
		return (
			<div>
				<Row>
					<HeatmapChart data={mockData} />
				</Row>
			</div>
		);
	}
}
