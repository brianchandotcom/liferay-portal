/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import classNames from 'classnames';
import {memo, useState} from 'react';
import DrilldownMenuItems from './components/DrilldownMenuItems';

const DropDownWithDrillDown = ({
	alignmentPosition,
	className,
	containerElement,
	initialActiveMenu,
	menuElementAttrs,
	menuHeight,
	menuWidth,
	menus,
	offsetFn,
	trigger,
}) => {
	const [activeMenu, setActiveMenu] = useState(initialActiveMenu);
	const [direction, setDirection] = useState();
	const [history, setHistory] = useState([]);
	const [active, setActive] = useState(false);

	const menuIds = Object.keys(menus);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={alignmentPosition}
			className={className}
			containerElement={containerElement}
			hasRightSymbols
			menuElementAttrs={{
				...menuElementAttrs,
				className: classNames(
					menuElementAttrs?.className,
					'drilldown drop-down-menu-items p-0'
				),
			}}
			menuHeight={menuHeight}
			menuWidth={menuWidth}
			offsetFn={offsetFn}
			onActiveChange={setActive}
			trigger={trigger}
		>
			<div>
				{menuIds.map((menuKey) => {
					return (
						<DrilldownMenuItems
							active={activeMenu === menuKey}
							direction={direction}
							header={
								activeMenu === menuKey && !!history.length
									? history.slice(-1)[0].title
									: undefined
							}
							items={menus[menuKey]}
							key={menuKey}
							onBack={() => {
								const [parent] = history.slice(-1);

								setHistory(
									history.slice(0, history.length - 1)
								);

								setDirection('prev');

								setActiveMenu(parent.id);
							}}
							onForward={(title, childId) => {
								setHistory([
									...history,
									{id: activeMenu, title},
								]);

								setDirection('next');

								setActiveMenu(childId);
							}}
						/>
					);
				})}
			</div>
		</ClayDropDown>
	);
};

export default memo(DropDownWithDrillDown);
