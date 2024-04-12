/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelectWithOption} from '@clayui/form';

function Jethr0SelectWithOption({
	ariaLabel,
	disabled = false,
	id,
	onChange,
	options,
	value,
}) {
	if (disabled) {
		return (
			<ClaySelectWithOption
				aria-label={ariaLabel}
				disabled={disabled}
				id={id}
				onChange={onChange}
				options={options}
				value={value}
			/>
		);
	}

	return (
		<ClaySelectWithOption
			aria-label={ariaLabel}
			id={id}
			onChange={onChange}
			options={options}
			value={value}
		/>
	);
}

export default Jethr0SelectWithOption;
