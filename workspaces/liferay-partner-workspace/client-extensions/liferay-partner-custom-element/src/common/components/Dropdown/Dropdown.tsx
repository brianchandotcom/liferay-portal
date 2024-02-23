/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';

export interface DropdownOption {
	icon?: string;
	key: string;
	label: string;
	onClick: () => void;
}

interface Props {
	className?: string;
	closeOnClick?: boolean;
	icon?: string;
	label?: string;
	onClick?: () => void;
	options: DropdownOption[];
}
const DropDown = ({className, closeOnClick, icon, label, options}: Props) => (
	<ClayDropDown
		className={className}
		closeOnClick={closeOnClick}
		trigger={
			<ClayButton aria-label="Action Button" displayType="unstyled">
				{label && (
					<span className="dislay-inline-block mr-1"> {label} </span>
				)}
				{icon && <ClayIcon symbol={icon}></ClayIcon>}
			</ClayButton>
		}
	>
		<ClayDropDown.ItemList>
			<ClayDropDown.Group>
				{options.map((item, index) => (
					<ClayDropDown.Item key={index} onClick={item.onClick}>
						{item.icon && <ClayIcon symbol={item.icon}></ClayIcon>}

						{item.label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.Group>
		</ClayDropDown.ItemList>
	</ClayDropDown>
);

export default DropDown;
