/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React from 'react';
import {CHART_COLORS} from '~/shared/util/charts';

type TItems = {
	change: number;
	id: string;
	secondaryInfo: string;
	title: string;
};

interface IChangeLegendProps {
	className: string;
	items: TItems[];
}

export default class ChangeLegend extends React.Component<IChangeLegendProps> {
	render() {
		const {className, items} = this.props;

		return (
			<div className={getCN('change-legend-root', className)}>
				{items.map((item, i) => {
					const {change, id, secondaryInfo, title} = item;

					const decrease = change < 0;

					const percentChange = isFinite(change)
						? `${Math.abs(change * 100).toFixed(1)}%`
						: '--';

					return (
						<div className={`legend-item ${id}`} key={id}>
							<div className="legend-header">
								<span
									className="legend-color"
									style={{backgroundColor: CHART_COLORS[i]}}
								/>

								<span className="title">{title}</span>
							</div>

							<div className="secondary-info">
								{secondaryInfo}

								<span
									className={getCN('change', {
										decrease,
										increase: change > 0,
									})}
								>
									{!!change && !isNaN(change) && (
										<ClayIcon
											className="icon-root"
											symbol={
												decrease
													? 'caret-bottom'
													: 'caret-top'
											}
										/>
									)}

									{percentChange}
								</span>
							</div>
						</div>
					);
				})}
			</div>
		);
	}
}
