/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {ReactElement, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {Dropdown} from '~/atoms';

type DropDownProps = {
	items: Dropdown;
	position?: any;
	trigger: ReactElement;
};

const DropDown: React.FC<DropDownProps> = ({
	items,
	position = Align.BottomCenter,
	trigger,
}) => {
	const navigate = useNavigate();
	const [active, setActive] = useState(false);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={position}
			onActiveChange={setActive}
			trigger={trigger}
		>
			<ClayDropDown.ItemList>
				{items.map((section, index) => (
					<div key={index}>
						<ClayDropDown.Group header={section.title}>
							{section.items.map(
								(
									{divider, icon, label, onClick, path},
									itemIndex
								) => (
									<React.Fragment key={itemIndex}>
										<ClayDropDown.Item
											onClick={() => {
												if (onClick) {
													setActive(false);

													return onClick();
												}

												if (!path) {
													return;
												}

												const isHttpUrl = path.startsWith(
													'http'
												);

												if (isHttpUrl) {
													window.location.href = path;

													return;
												}

												setActive(false);

												navigate(path);
											}}
										>
											<div className="align-items-center d-flex testray-sidebar-item text-dark">
												{icon && (
													<ClayIcon
														fontSize={16}
														symbol={icon}
													/>
												)}

												<span className="ml-1 tr-sidebar__text">
													{label}
												</span>
											</div>
										</ClayDropDown.Item>

										{divider && <ClayDropDown.Divider />}
									</React.Fragment>
								)
							)}
						</ClayDropDown.Group>

						{items.length - 1 !== index && <ClayDropDown.Divider />}
					</div>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

export default DropDown;
