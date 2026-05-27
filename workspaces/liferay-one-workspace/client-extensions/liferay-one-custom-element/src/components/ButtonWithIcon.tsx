/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {ComponentProps, ForwardedRef, ReactNode} from 'react';

type ButtonWithIconProps = {
	children?: ReactNode;
	iconProps?: Omit<ComponentProps<typeof ClayIcon>, 'symbol'>;
	symbol: string;
} & React.ComponentProps<typeof ClayButton>;

const ButtonWithIcon = React.forwardRef<HTMLButtonElement, ButtonWithIconProps>(
	(
		{children, iconProps, symbol = 'plus', ...props},
		ref: ForwardedRef<HTMLButtonElement>
	) => {
		return (
			<ClayButton {...props} ref={ref}>
				<ClayIcon className="mr-2" symbol={symbol} {...iconProps} />

				{children}
			</ClayButton>
		);
	}
);

export default ButtonWithIcon;
