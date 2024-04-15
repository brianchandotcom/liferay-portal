/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';

import './index.scss';

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

				<div className="d-flex justify-content-end mb-1">
					<ClayButtonWithIcon
						aria-labelledby="angle-down"
						className="align-self-end d-flex"
						displayType="unstyled"
						symbol="ellipsis-v"
					/>

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
