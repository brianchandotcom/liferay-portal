/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';

import './index.scss';

import {HTMLAttributes} from 'react';

interface SectionProps extends HTMLAttributes<HTMLDivElement> {
	name: string;
}

export function Section({name, ...props}: SectionProps) {
	return (
		<div className="marketplace-form-section mt-4 p-1 rounded" {...props}>
			<div className="d-flex justify-content-between">
				<div className="d-flex inline-item justify-content-start">
					<div className="arrow-container ml-4">
						<ClayButtonWithIcon
							aria-label="arrow-up"
							displayType="unstyled"
							size="sm"
							symbol="order-arrow-up"
						/>

						<ClayButtonWithIcon
							aria-label="arrow-down"
							displayType="unstyled"
							size="sm"
							symbol="order-arrow-down"
						/>
					</div>

					<b className="ml-4">{name}</b>
				</div>

				<div className="d-flex justify-content-end">
					<ClayButtonWithIcon
						aria-labelledby="angle-down"
						className="align-self-end d-flex"
						displayType="unstyled"
						symbol="ellipsis-v"
					/>

					<ClayButtonWithIcon
						aria-labelledby="angle-down"
						className="align-self-end d-flex"
						displayType="unstyled"
						symbol="angle-right"
					/>
				</div>
			</div>
		</div>
	);
}
