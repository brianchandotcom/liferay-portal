/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {LabelHTMLAttributes} from 'react';

import './index.scss';

interface LabelProps extends LabelHTMLAttributes<HTMLLabelElement> {
	required?: boolean;
}

export function Label({required, ...props}: LabelProps) {
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
		</>
	);
}
