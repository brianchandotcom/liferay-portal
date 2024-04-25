/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {LabelHTMLAttributes} from 'react';

import './index.scss';

import {ClayTooltipProvider} from '@clayui/tooltip';

interface LabelProps extends LabelHTMLAttributes<HTMLLabelElement> {
	info?: string;
	required?: boolean;
}

export function Label({info, required, ...props}: LabelProps) {
	return (
		<>
			<label {...props} />
			{required && (
				<ClayIcon
					className="required-icon text-danger"
					symbol="asterisk
"
				/>
			)}

			{info && (
				<ClayTooltipProvider>
					<div
						className="info-bg inline-item inline-item-after mb-1"
						data-tooltip-align="top"
						title={info}
					>
						<ClayIcon
							className="info-icon"
							symbol="info-circle-open
"
						/>
					</div>
				</ClayTooltipProvider>
			)}
		</>
	);
}
