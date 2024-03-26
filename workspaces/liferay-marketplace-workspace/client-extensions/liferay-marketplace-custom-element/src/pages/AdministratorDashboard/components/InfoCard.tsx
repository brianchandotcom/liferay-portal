/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import DropDown from '@clayui/drop-down/lib/DropDown';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

type InfoCard = {
	className?: string;
	dropDownItems?: any[];
	growth?: number;
	growthContext?: string;
	onSelectDropDown?: any;
	symbol: string;
	title: string;
	value: string | number;
};

const InfoCard: React.FC<InfoCard> = ({
	className,
	dropDownItems,
	growth = 0,
	growthContext,
	onSelectDropDown,
	symbol,
	title,
	value,
}) => {
	return (
		<div
			className={classNames(
				`p-4 d-flex flex-column justify-content-between info-card ${className}`
			)}
		>
			<div className="align-content-center d-flex justify-content-between mb-2">
				<span className="d-flex flex-column">
					<div className="d-flex">
						<span className="font-weight-lighter mb-2 mr-2 text-black-50">
							{title}
						</span>
						{dropDownItems?.length && onSelectDropDown && (
							<DropDown
								closeOnClick
								filterKey="name"
								trigger={
									<ClayButtonWithIcon
										aria-label="drop down"
										symbol="caret-bottom"
										title="Close"
									/>
								}
							>
								<DropDown.ItemList items={dropDownItems}>
									{(item) => (
										<DropDown.Item
											key={item as string}
											onClick={() => {
												onSelectDropDown(item);
											}}
										>
											{item as string}
										</DropDown.Item>
									)}
								</DropDown.ItemList>
							</DropDown>
						)}
					</div>
					<span className="font-weight-bold h2">{value}</span>
				</span>
				<span className="d-flex flex-column justify-content-center">
					<ClayIcon
						className="text-primary"
						fontSize={32}
						symbol={symbol}
					/>
				</span>
			</div>

			{growthContext && (
				<div className="font-weight-bold text-black-50">
					<span
						className={classNames('mr-2', {
							'text-danger': growth < 0,
							'text-success': growth > 0,
						})}
					>
						<ClayIcon
							className="mr-21"
							symbol={
								growth > 0
									? 'order-arrow-up'
									: 'order-arrow-down'
							}
						/>
						{growth}%
					</span>
					<span>{growthContext}</span>
				</div>
			)}
		</div>
	);
};

export default InfoCard;
