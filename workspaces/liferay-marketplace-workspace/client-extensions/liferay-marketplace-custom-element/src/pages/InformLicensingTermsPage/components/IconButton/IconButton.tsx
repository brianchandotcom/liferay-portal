/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {ComponentProps, ReactNode} from 'react';

type IconButtonProps = {
	children?: ReactNode;
	symbol?: string;
} & ComponentProps<typeof ClayButton>;

const IconButton: React.FC<IconButtonProps> = ({
	children,
	symbol = 'plus',
	...props
}) => {
	return (
		<ClayButton {...props}>
			<ClayIcon className="mr-2" symbol={symbol} />

			{children}
		</ClayButton>
	);
};

export default IconButton;
