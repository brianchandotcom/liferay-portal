/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button, {ClayButtonWithIcon} from '@clayui/button';
import DrilldownMenu, {IItem} from '@clayui/drop-down/lib/drilldown/Menu';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';
import {CSSTransition} from 'react-transition-group';

interface MenuItem extends Omit<IItem, 'child' | 'type'> {
	child?: string | JSX.Element;
	type?: 'divider' | 'component';
}

interface IProps {
	items: MenuItem[];
}

const DrilldownMenuItems = ({
	active,
	direction,
	header,
	items,
	onBack,
	onForward,
}: Omit<React.ComponentProps<typeof DrilldownMenu>, 'items'> & IProps) => {
	const initialClasses = classNames('transitioning', {
		'drilldown-prev-initial': direction === 'prev',
	});

	return (
		<CSSTransition
			className={classNames('drilldown-item', {
				'drilldown-current': active,
			})}
			classNames={{
				enter: initialClasses,
				enterActive: `drilldown-transition drilldown-${direction}-active`,
				exit: initialClasses,
				exitActive: `drilldown-transition drilldown-${direction}-active`,
			}}
			in={active}
			timeout={250}
		>
			<div className="drilldown-item-inner">
				{header && (
					<>
						<div
							className="dropdown-header text-neutral-8"
							onClick={onBack}
						>
							<ClayButtonWithIcon
								className="component-action dropdown-item-indicator-start text-neutral-2"
								onClick={onBack}
								symbol="angle-left-small"
							/>

							<span className="dropdown-item-indicator-text-start pl-3 text-capitalize text-neutral-8">
								{header}
							</span>
						</div>
					</>
				)}

				{items && (
					<ul className="inline-scroller">
						{items.map(
							(
								{
									child,
									className,
									disabled,
									onClick,
									symbol,
									title,
									type,
								},
								index
							) =>
								type === 'divider' ? (
									<li
										aria-hidden="true"
										className="dropdown-divider"
										key={`${index}-divider`}
										role="presentation"
									/>
								) : type === 'component' ? (
									<React.Fragment key={`${index}-${title}`}>
										{child}
									</React.Fragment>
								) : (
									<li key={`${index}-${title}`}>
										<Button
											className={classNames(
												'dropdown-item',
												className
											)}
											disabled={disabled}
											displayType="unstyled"
											onClick={(event) => {
												if (onClick) {
													onClick(event);
												}

												if (
													title &&
													typeof child === 'string'
												) {
													onForward(title, child);
												}
											}}
										>
											{symbol && (
												<span className="dropdown-item-indicator-start">
													<ClayIcon symbol={symbol} />
												</span>
											)}

											<span className="dropdown-item-indicator-text-end">
												{title}
											</span>

											{child && (
												<span className="dropdown-item-indicator-end">
													<ClayIcon symbol="angle-right" />
												</span>
											)}
										</Button>
									</li>
								)
						)}
					</ul>
				)}
			</div>
		</CSSTransition>
	);
};

export default DrilldownMenuItems;
