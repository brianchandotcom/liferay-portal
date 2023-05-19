/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
