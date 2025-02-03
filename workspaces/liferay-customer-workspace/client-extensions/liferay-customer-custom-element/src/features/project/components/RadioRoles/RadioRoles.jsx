/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayRadio} from '@clayui/form';

const RadioRoles = ({onChange, selected, ...props}) => {
	return (
		<ClayRadio
			{...props}
			checked={selected}
			disabled={false}
			onChange={onChange}
		/>
	);
};

export default RadioRoles;
