/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropdown, {Align} from '@clayui/drop-down';
import getCN from 'classnames';
import React, {useEffect, useState} from 'react';

import Header from './Header';
import SearchableList from './SearchableList';

interface IBaseDropdownProps {
	alignmentPosition?: (typeof Align)[keyof typeof Align];
	children: (bag: {
		active: boolean;
		setActive: (v: boolean) => void;
	}) => React.ReactNode;
	className?: string;
	onActiveChange?: (active: boolean) => void;
	trigger: React.ReactElement;
}

const BaseDropdown: React.FC<IBaseDropdownProps> = ({
	alignmentPosition = Align.RightTop,
	children,
	className,
	onActiveChange,
	trigger,
}) => {
	const [active, setActive] = useState(false);

	useEffect(() => {
		if (onActiveChange) {
			onActiveChange(active);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [active]);

	return (
		<ClayDropdown
			active={active}
			alignmentPosition={alignmentPosition}
			menuElementAttrs={{
				className: getCN('base-dropdown-menu-root', className),
			}}
			onActiveChange={setActive}
			trigger={trigger}
		>
			{children({active, setActive})}
		</ClayDropdown>
	);
};

export default Object.assign(BaseDropdown, {
	Header,
	SearchableList,
});
