/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import classNames from 'classnames';
import {JSXElementConstructor, memo, useState} from 'react';

import FilterNavigation from './components/FilterNavigation';

import type {AlignPoints} from '@clayui/shared';

interface IProps {
	alignmentPosition?: number | undefined;
	className?: string;
	containerElement?: JSXElementConstructor<any> | 'div' | 'li' | undefined;
	initialActiveMenu: string;
	menuElementAttrs?: React.HTMLAttributes<HTMLElement>;
	menuHeight?: 'auto' | undefined;
	menuWidth?: 'sm' | 'shrink' | 'full' | undefined;
	menus: Record<
		string,
		{
			child?: React.ReactNode;
			title: string;
			type?: 'divider' | 'component' | 'item';
		}[]
	>;
	offsetFn?: (points: AlignPoints) => [number, number];
	trigger: React.ReactElement;
}

const FilterDropdown: React.FC<IProps> = ({
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
	const [activeMenu, setActiveMenu] = useState<string>(initialActiveMenu);
	const [direction, setDirection] = useState<'prev' | 'next'>('next');
	const [history, setHistory] = useState<{id: string; title: string}[]>([]);
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
				{menuIds.map((menuKey) => (
					<FilterNavigation
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

							setDirection('next');

							parent && setActiveMenu(parent.id);
						}}
						onForward={(title, child) => {
							setHistory([...history, {id: activeMenu, title}]);

							setDirection('prev');

							setActiveMenu(child as string);
						}}
					/>
				))}
			</div>
		</ClayDropDown>
	);
};

export default memo(FilterDropdown);
