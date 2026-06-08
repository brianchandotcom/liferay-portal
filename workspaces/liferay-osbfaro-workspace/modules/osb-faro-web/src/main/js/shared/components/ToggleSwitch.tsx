/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {noop} from 'lodash';
import React from 'react';

interface IToggleSwitchProps extends React.HTMLAttributes<HTMLElement> {
	checked?: boolean;
	label?: string;
	name: string;
	onChange?: (event: React.FormEvent<HTMLInputElement>) => void;
}

const ToggleSwitch: React.FC<IToggleSwitchProps> = ({
	checked = false,
	className,
	label,
	name,
	onChange = noop,
}) => (

	// eslint-disable-next-line jsx-a11y/label-has-associated-control
	<label
		className={getCN('toggle-switch toggle-switch-root', className)}
		htmlFor={name}
	>
		<input
			checked={checked}
			className="toggle-switch-check"
			data-testid="toggle-switch-input"
			id={name}
			name={name}
			onChange={onChange}
			type="checkbox"
			value={checked.toString()}
		/>

		<span aria-hidden="true" className="toggle-switch-bar">
			<span
				className="toggle-switch-handle"
				data-label-off={label}
				data-label-on={label}
			/>
		</span>
	</label>
);

export default ToggleSwitch;
