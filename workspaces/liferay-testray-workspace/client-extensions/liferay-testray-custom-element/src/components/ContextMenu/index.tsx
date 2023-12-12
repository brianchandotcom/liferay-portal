/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown, {Align} from '@clayui/drop-down';
import React, {useState} from 'react';

import DropDownAction from '../DropDown/DropDownAction';

export type ContextMenuState = {
	actions: any[];
	item: any;
	position: {
		x: number;
		y: number;
	};
	rowIndex: number;
	visible: boolean;
};

type ContextMenuProps = {
	contextMenuState: ContextMenuState;
	mutate: any;
	setContextMenuState: React.Dispatch<ContextMenuState>;
};

const ContextMenu: React.FC<ContextMenuProps> = ({
	contextMenuState,
	mutate,
	setContextMenuState,
}) => {
	const [active, setActive] = useState(true);

	if (!contextMenuState.visible) {
		return null;
	}

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.RightCenter}
			className="dropdown-action"
			onActiveChange={setActive}
			style={{
				left: contextMenuState.position.x - 40,
				position: 'fixed',
				top: contextMenuState.position.y,
			}}
			trigger={
				<div onContextMenu={(event) => event.preventDefault()}></div>
			}
		>
			<ClayDropDown.ItemList>
				{contextMenuState.actions.map((action, index) => (
					<DropDownAction
						action={action}
						item={contextMenuState.item}
						key={index}
						mutate={mutate}
						onBeforeClickAction={() =>
							setContextMenuState({
								...contextMenuState,
								visible: false,
							})
						}
						setActive={(active) => {
							setActive(active);

							if (!active) {
								setContextMenuState({
									...contextMenuState,
									visible: false,
								});
							}
						}}
					/>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

export default ContextMenu;
