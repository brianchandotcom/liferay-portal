/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';

export interface DropdownOption {
	icon: string;
	key: string;
	label: string;
	onClick: () => void;
}

interface Props {
	closeOnClick?: boolean;
	onClick?: () => void;
	options: DropdownOption[];
}

const DropDown = ({closeOnClick, options}: Props) => (
	<ClayDropDown
		closeOnClick={closeOnClick}
		trigger={
			<ClayButton displayType="unstyled">
				<ClayIcon symbol="ellipsis-v"></ClayIcon>
			</ClayButton>
		}
	>
		<ClayDropDown.ItemList>
			<ClayDropDown.Group>
				{options.map((item, index) => (
					<ClayDropDown.Item key={index} onClick={item.onClick}>
						<ClayIcon symbol={item.icon}></ClayIcon>

						{item.label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.Group>
		</ClayDropDown.ItemList>
	</ClayDropDown>
);

export default DropDown;
