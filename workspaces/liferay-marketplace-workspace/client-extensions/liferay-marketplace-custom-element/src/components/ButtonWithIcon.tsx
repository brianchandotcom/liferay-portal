/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {ForwardedRef, ReactNode} from 'react';

type ButtonWithIconProps = {
	children?: ReactNode;
	className?: string;
	symbol: string;
} & React.ComponentProps<typeof ClayButton>;

const ButtonWithIcon = React.forwardRef<HTMLButtonElement, ButtonWithIconProps>(
	(
		{children, className, symbol = 'plus', ...props},
		ref: ForwardedRef<HTMLButtonElement>
	) => {
		return (
			<ClayButton className={className} ref={ref} {...props}>
				<ClayIcon className="mr-2" symbol={symbol} />

				{children}
			</ClayButton>
		);
	}
);

export default ButtonWithIcon;
