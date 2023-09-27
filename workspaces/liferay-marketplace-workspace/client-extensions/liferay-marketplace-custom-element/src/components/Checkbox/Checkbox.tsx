/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';

import './Checkbox.scss';

interface CheckboxProps {
	checked: boolean;
	description?: string;
	label?: string;
	onChange: () => void;
	readOnly?: boolean;
}

export function Checkbox({
	checked,
	description,
	label,
	onChange,
	readOnly = false,
}: CheckboxProps) {
	return (
		<div className="checkbox-base-container">
			<ClayCheckbox
				checked={checked}
				onChange={() => onChange()}
				readOnly={readOnly}
			/>

			<div className="checkbox-texts-container">
				<span className="checkbox-label-text">{label}</span>

				<span className="checkbox-description-text">{description}</span>
			</div>
		</div>
	);
}
