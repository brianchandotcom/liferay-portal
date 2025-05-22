/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {ComponentProps} from 'react';

export default function ExternalLink({
	children,
	...otherProps
}: ComponentProps<typeof ClayLink>) {
	return (
		<ClayLink {...otherProps} displayType="secondary" target="_blank">
			{children}

			<ClayIcon className="ml-1" fontSize={8} symbol="shortcut" />
		</ClayLink>
	);
}
