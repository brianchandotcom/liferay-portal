/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {Button as ClayButton} from '@clayui/core';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';

const ButtonDropDown = ({
	label,
	align = Align.BottomRight,
	active,
	setActive,
	items,
	...props
}) => (
	<ClayDropDown
		active={active}
		alignmentPosition={align}
		onActiveChange={setActive}
		trigger={
			<ClayButton className="btn btn-primary px-3 py-2">
				{label}

				<ClayIcon className="ml-2" symbol="caret-bottom" />
			</ClayButton>
		}
		{...props}
	>
		<ClayDropDown.ItemList>
			{items?.map(({icon, label, onClick}) => (
				<ClayDropDown.Item
					className="cp-activation-keys-drop-down-item font-weight-semi-bold px-3 rounded-xs text-neutral-8"
					key={label}
					onClick={onClick}
				>
					{icon}

					{label}
				</ClayDropDown.Item>
			))}
		</ClayDropDown.ItemList>
	</ClayDropDown>
);

export default ButtonDropDown;
