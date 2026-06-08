/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import {isFinite} from 'lodash';
import React from 'react';
import InfoPopover from '~/shared/components/InfoPopover';
import {formatChange} from '~/shared/util/change';
import {sub} from '~/shared/util/lang';

interface ITrendItemProps {
	change: number;
	data: number[];
	id: string;
	info?: {content: string; title: string};
	title: string;
	total: number;
}

export const TrendItem = function TrendItem({
	change,
	info,
	title,
	total,
}: ITrendItemProps) {
	const finiteChange = isFinite(change);

	return (
		<div className="trend-item-root" key={title}>
			<div className="d-flex justify-content-between trend-item-title">
				<div className="card-title">{title}</div>

				{info && <InfoPopover {...info} />}
			</div>

			<div className="align-items-center d-flex flex-grow-1 justify-content-center">
				<div className="total">{total.toLocaleString()}</div>
			</div>

			{!!total && (
				<div className="change description">
					{sub(
						Liferay.Language.get('x-vs-previous-30-days'),
						[
							<span
								className={getCN({
									decrease: change < 0 && finiteChange,
									increase: change > 0 && finiteChange,
								})}
								key="CHANGE"
							>
								{finiteChange && !!change && (
									<ClayIcon
										className="icon-root"
										symbol={
											change > 0
												? 'caret-top'
												: 'caret-bottom'
										}
									/>
								)}

								<b>
									{finiteChange
										? `${formatChange(change)}%`
										: '--'}
								</b>
							</span>,
						],
						false
					)}
				</div>
			)}
		</div>
	);
};

const TypeTrend: React.FC<{items: ITrendItemProps[]}> = ({items}) => (
	<div className="type-trend-root">
		{items.map((item, i) => (
			<TrendItem {...item} key={i} />
		))}
	</div>
);

TypeTrend.defaultProps = {
	items: [],
};

export default TypeTrend;
