/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown, {ClayDropDownWithDrilldown} from '@clayui/drop-down';
import classNames from 'classnames';
import {memo, useEffect, useRef, useState} from 'react';

import DrilldownMenuItems from './components/DrilldownMenuItems/DrilldownMenuItems';

type History = {
	id: string;
	title: string;
};

type Menu = {
	[id: string]: React.ComponentProps<typeof DrilldownMenuItems>['items'];
};

interface IProps {
	menus: Menu;
}

const DropDownWithDrillDown = ({
	alignmentPosition,
	className,
	containerElement,
	defaultActiveMenu = 'x0a0',
	menuElementAttrs,
	menuHeight,
	menuWidth,
	menus,
	offsetFn,
	trigger,
}: Omit<React.ComponentProps<typeof ClayDropDownWithDrilldown>, 'menus'> &
	IProps) => {
	const [activeMenu, setActiveMenu] = useState(defaultActiveMenu);
	const [direction, setDirection] = useState<'prev' | 'next'>();
	const [history, setHistory] = useState<Array<History>>([]);
	const [active, setActive] = useState<boolean>(false);

	const focusHistoryRef = useRef<Array<HTMLElement>>([]);

	const innerRef = useRef<HTMLDivElement>(null);

	const isKeyboardRef = useRef<boolean>(false);

	const menuIds = Object.keys(menus);

	useEffect(() => {
		if (!isKeyboardRef.current) {
			return;
		}

		if (innerRef.current) {
			if (direction === 'prev') {
				const [previous] = focusHistoryRef.current.slice(-1);

				focusHistoryRef.current = focusHistoryRef.current.slice(
					0,
					focusHistoryRef.current.length - 1
				);

				previous?.focus();
			}
			else {
				const itemEl = innerRef.current.querySelector<HTMLElement>(
					'.drilldown-current a.dropdown-item, .drilldown-current button.dropdown-item'
				);

				focusHistoryRef.current = [
					...focusHistoryRef.current,
					document.activeElement as HTMLElement,
				];
				itemEl?.focus();
			}
		}
	}, [direction]);

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
				{menuIds.map((menuKey) => (
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

							setHistory(history.slice(0, history.length - 1));

							setDirection('prev');

							setActiveMenu(parent.id);
						}}
						onForward={(title, childId) => {
							setHistory([
								...history,
								{id: activeMenu, title} as History,
							]);

							setDirection('next');

							setActiveMenu(childId);
						}}
						onKeyDown={(event) => {
							if (event.key !== 'Enter') {
								isKeyboardRef.current = false;
							}
							else {
								isKeyboardRef.current = true;
							}
						}}
					/>
				))}
			</div>
		</ClayDropDown>
	);
};

export default memo(DropDownWithDrillDown);
