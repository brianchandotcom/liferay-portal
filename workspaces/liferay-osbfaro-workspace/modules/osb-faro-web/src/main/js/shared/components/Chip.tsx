/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React from 'react';

export interface IChipProps {
	children?: React.ReactNode;
	className?: string;
	iconSymbol?: string;
	onCloseClick: () => void;
}

const Chip = React.forwardRef<HTMLDivElement, IChipProps>(
	({children, className, iconSymbol = 'times-circle', onCloseClick}, ref) => (
		<div
			className={getCN(
				'chip-root d-flex align-items-center justify-content-between',
				className
			)}
			ref={ref}
		>
			{children}

			<ClayButton
				aria-label={Liferay.Language.get('close')}
				className="button-root remove-button"
				displayType="unstyled"
				onClick={onCloseClick}
			>
				<ClayIcon className="icon-root" symbol={iconSymbol} />
			</ClayButton>
		</div>
	)
);

export default Chip;
