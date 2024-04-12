/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';

function Jethr0Input({
	disabled = false,
	id,
	onChange,
	placeholder,
	type,
	value,
}) {
	if (disabled) {
		return (
			<ClayInput
				disabled={disabled}
				id={id}
				onChange={onChange}
				placeholder={placeholder}
				type={type}
				value={value}
			/>
		);
	}

	return (
		<ClayInput
			id={id}
			onChange={onChange}
			placeholder={placeholder}
			type={type}
			value={value}
		/>
	);
}

export default Jethr0Input;
