/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';

import './index.scss';

import ClayDropDown from '@clayui/drop-down';
import {HTMLAttributes, useState} from 'react';

import {BLOCK_DIRECTIONS} from '../../context/SolutionContext';

interface SectionWithControllersProps extends HTMLAttributes<HTMLDivElement> {
	dropdownItems: {
		disabled?: boolean;
		name: string;
		onClick?: () => void;
	}[];
	index: number;
	name: string;
	onArrowClick: (direction: BLOCK_DIRECTIONS) => void;
	position: number;
}

export function SectionWithControllers({
	children,
	dropdownItems,
	index,
	name,
	onArrowClick,
	position,
	...props
}: SectionWithControllersProps) {
	const [collapsed, setCollapsed] = useState(true);

	return (
		<div className="marketplace-form-section mt-4 p-0" {...props}>
			<div className="controllers d-flex justify-content-between">
				<div className="d-flex inline-item justify-content-start">
					<div className="arrow-container ml-4">
						<ClayButtonWithIcon
							aria-label="arrow-up"
							disabled={index === 0}
							displayType="unstyled"
							onClick={() =>
								onArrowClick(BLOCK_DIRECTIONS.MOVE_UP)
							}
							size="sm"
							symbol="order-arrow-up"
						/>

						<ClayButtonWithIcon
							aria-label="arrow-down"
							disabled={index === position - 1}
							displayType="unstyled"
							onClick={() =>
								onArrowClick(BLOCK_DIRECTIONS.MOVE_DOWN)
							}
							size="sm"
							symbol="order-arrow-down"
						/>
					</div>

					<b className="ml-4">{name}</b>
				</div>

				<div className="align-self-center d-flex justify-content-end">
					<ClayDropDown
						closeOnClick
						trigger={
							<ClayButtonWithIcon
								aria-label="Menu"
								displayType={null}
								symbol="ellipsis-v"
								title="Menu"
							/>
						}
					>
						<ClayDropDown.ItemList>
							{dropdownItems.map((dropDownItem, index) => (
								<ClayDropDown.Item
									disabled={dropDownItem.disabled}
									key={index}
									onClick={dropDownItem.onClick}
								>
									{dropDownItem.name}
								</ClayDropDown.Item>
							))}
						</ClayDropDown.ItemList>
					</ClayDropDown>

					<ClayButtonWithIcon
						aria-labelledby="angle-right"
						className="align-self-end d-flex"
						displayType="unstyled"
						onClick={() => setCollapsed(!collapsed)}
						symbol={collapsed ? 'angle-down' : 'angle-right'}
					/>
				</div>
			</div>

			{collapsed && <div className="children-container">{children}</div>}
		</div>
	);
}
