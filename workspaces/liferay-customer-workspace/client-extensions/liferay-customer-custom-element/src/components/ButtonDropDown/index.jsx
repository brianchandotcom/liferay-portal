/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {Button} from '..';
import ClayDropDown, {Align} from '@clayui/drop-down';
import classNames from 'classnames';
import {useState} from 'react';

const ButtonDropDown = ({
	customDropDownButton,
	label,
	align = Align.BottomRight,
	items,
	...props
}) => {
	const [active, setActive] = useState(false);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={align}
			onActiveChange={setActive}
			trigger={
				customDropDownButton || (
					<Button
						appendIcon="caret-bottom"
						className="btn btn-primary"
					>
						{label}
					</Button>
				)
			}
			{...props}
		>
			<ClayDropDown.ItemList>
				{items.map(
					({
						customOptionStyle,
						disabled,
						icon,
						label,
						onClick,
						tooltip,
					}) => (
						<ClayDropDown.Item
							className={classNames(
								'font-weight-semi-bold text-paragraph-sm px-3 rounded-xs',
								customOptionStyle,
								{
									'cp-common-drop-down-item text-neutral-8': !disabled,
									'text-neutral-5': disabled,
								}
							)}
							disabled={disabled}
							key={label}
							onClick={onClick}
						>
							<div
								className="d-flex"
								title={disabled ? tooltip : null}
							>
								{icon && <div className="mr-1">{icon}</div>}

								{label}
							</div>
						</ClayDropDown.Item>
					)
				)}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

export default ButtonDropDown;
