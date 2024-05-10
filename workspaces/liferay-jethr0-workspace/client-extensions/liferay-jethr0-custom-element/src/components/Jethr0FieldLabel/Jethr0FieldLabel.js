/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';

function Jethr0FieldLabel({labelKey, labelName}) {
	return (
		<label htmlFor={labelKey}>
			<Text size={6} weight="normal">
				{labelName}
			</Text>
		</label>
	);
}

export default Jethr0FieldLabel;
