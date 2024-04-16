/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';

import './index.scss';

import ClayDropDown from '@clayui/drop-down';
import {HTMLAttributes, useState} from 'react';

interface SectionWithControllersProps extends HTMLAttributes<HTMLDivElement> {
	name: string;
}

export function SectionWithControllers({
	children,
	name,
	...props
}: SectionWithControllersProps) {
	const [openBody, setOpenBody] = useState(false);

	const dropDownItems = [
		{name: 'Move to Top'},
		{name: 'Move Up'},
		{name: 'Move Down'},
		{name: 'Move to Bottom'},
		{name: 'Delete'},
	];

	return (
		<div className="marketplace-form-section mt-4 p-0" {...props}>
			<div className="controllers d-flex justify-content-between">
				<div className="d-flex inline-item justify-content-start">
					<div className="arrow-container ml-4">
						<ClayButtonWithIcon
							aria-label="arrow-up"
							disabled={true}
							displayType="unstyled"
							size="sm"
							symbol="order-arrow-up"
						/>

						<ClayButtonWithIcon
							aria-label="arrow-down"
							disabled={true}
							displayType="unstyled"
							size="sm"
							symbol="order-arrow-down"
						/>
					</div>

					<b className="ml-4">{name}</b>
				</div>

				<div className="align-self-center d-flex justify-content-end">
					<ClayDropDown
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
							{dropDownItems.map((dropDownItem, index) => (
								<ClayDropDown.Item key={index}>
									{dropDownItem.name}
								</ClayDropDown.Item>
							))}
						</ClayDropDown.ItemList>
					</ClayDropDown>

					<ClayButtonWithIcon
						aria-labelledby="angle-right"
						className="align-self-end d-flex"
						displayType="unstyled"
						onClick={() => setOpenBody(!openBody ? true : false)}
						symbol={openBody ? 'angle-down' : 'angle-right'}
					/>
				</div>
			</div>

			{openBody && <div className="children-container">{children}</div>}
		</div>
	);
}
