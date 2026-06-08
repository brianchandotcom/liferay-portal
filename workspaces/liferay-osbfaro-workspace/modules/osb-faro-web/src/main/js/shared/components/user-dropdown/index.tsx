/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import {last} from 'lodash';
import React, {Fragment, useEffect, useRef, useState} from 'react';
import {CSSTransition, TransitionGroup} from 'react-transition-group';
import {getInitials} from '~/shared/util/util';

import Sticker from '../Sticker';
import Item from './Item';

import type {MenuItem, Menus} from './types';

interface IUserDropdownProps extends React.HTMLAttributes<HTMLElement> {
	alignmentPosition?: React.ComponentProps<
		typeof ClayDropDown
	>['alignmentPosition'];
	containerElement?: React.ComponentProps<
		typeof ClayDropDown
	>['containerElement'];
	initialActiveMenu: string;
	menus: Menus;
	showCaret?: boolean;
	userName: string;
}

interface ILabelProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
	showCaret?: boolean;
	userName: string;
}

const Label = React.forwardRef<HTMLButtonElement, ILabelProps>(
	({className, showCaret, userName, ...otherProps}, ref) => (
		<button
			className={getCN(
				'user-menu button-root btn btn-unstyled trigger',
				className
			)}
			ref={ref}
			type="button"
			{...otherProps}
		>
			<div className="text-truncate">
				<Sticker circle className="avatar">
					{getInitials(userName)}
				</Sticker>

				<span className="user-name">{userName}</span>

				{showCaret && (
					<ClayIcon
						className="caret icon-root"
						symbol="caret-bottom"
					/>
				)}
			</div>
		</button>
	)
);

const userDropDown: React.FC<IUserDropdownProps> = ({
	alignmentPosition = Align.RightCenter,
	className,
	containerElement: ContainerElement = 'div',
	initialActiveMenu,
	menus,
	showCaret = false,
	userName,
}: IUserDropdownProps) => {
	const [active, setActive] = useState(false);

	const [activeMenu, setActiveMenu] = useState(initialActiveMenu);

	const [direction, setDirection] = useState<'left' | 'right'>('left');

	const [history, setHistory] = useState<string[]>([initialActiveMenu]);

	useEffect(() => {
		if (!active) {
			setDirection('left');

			setHistory([initialActiveMenu]);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [active]);

	useEffect(() => {
		setActiveMenu(last(history) ?? initialActiveMenu);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [history]);

	const triggerElementRef = useRef(null);

	const menuElementRef = useRef(null);

	const handleActive = () => {
		setActive(!active);
	};

	return (
		<>
			<ContainerElement className={className}>
				<Label
					onClick={handleActive}
					ref={triggerElementRef}
					showCaret={showCaret}
					userName={userName}
				/>
			</ContainerElement>

			<ClayDropDown.Menu
				active={active}
				alignElementRef={triggerElementRef}
				alignmentPosition={alignmentPosition}
				className="user-menu-dropdown"
				onSetActive={setActive}
				ref={menuElementRef}
			>
				<TransitionGroup className="transition-carousel-group">
					<CSSTransition
						classNames={`transition-carousel-slide-in-out-${direction}`}
						key={activeMenu}
						timeout={250}
					>
						<div className="w-100">
							<ClayDropDown.ItemList>
								{initialActiveMenu !== activeMenu && (
									<ClayDropDown.Group>
										<ClayDropDown.Item>
											<ClayButton
												aria-label={Liferay.Language.get(
													'back'
												)}
												block
												className="button-root"
												displayType="unstyled"
												onClick={() => {
													setHistory(
														history.slice(0, -1)
													);

													setDirection('right');
												}}
											>
												<ClayIcon symbol="order-arrow-left" />
											</ClayButton>
										</ClayDropDown.Item>
									</ClayDropDown.Group>
								)}

								{menus[activeMenu].map(
									({items, subheaderLabel}, i) => (
										<ClayDropDown.Group
											header={subheaderLabel}
											key={i}
										>
											{items.map(
												(
													{
														childMenuId,
														divider,
														onClick,
														...otherProps
													},
													i
												) => (
													<Fragment key={i}>
														<Item
															{...otherProps}
															onClick={() => {
																if (
																	childMenuId
																) {
																	setHistory([
																		...history,
																		childMenuId,
																	]);

																	setDirection(
																		'left'
																	);
																}

																onClick &&
																	onClick();
															}}
														/>

														{divider && (
															<ClayDropDown.Divider />
														)}
													</Fragment>
												)
											)}
										</ClayDropDown.Group>
									)
								)}
							</ClayDropDown.ItemList>
						</div>
					</CSSTransition>
				</TransitionGroup>
			</ClayDropDown.Menu>
		</>
	);
};

export {MenuItem, Menus};
export default userDropDown;
