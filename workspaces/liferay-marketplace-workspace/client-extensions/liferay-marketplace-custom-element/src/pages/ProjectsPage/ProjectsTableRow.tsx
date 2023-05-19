/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import DropDown from '@clayui/drop-down';
import ClayTable from '@clayui/table';
import classNames from 'classnames';

import arrowDownIcon from '../../assets/icons/arrow_down_icon.svg';
import circleFillIcon from '../../assets/icons/circle_fill_icon.svg';
import liferayIcon from '../../assets/icons/liferay_icon.svg';
import {Tag} from '../../components/Tag/Tag';

import './ProjectsTableRow.scss';

interface ProjectsTableRowProps {
	author: string;
	createdAt: string;
	endDate: string;
	projectName: string;
	status: string;
}

export function ProjectsTableRow({
	author,
	createdAt,
	endDate,
	projectName,
	status,
}: ProjectsTableRowProps) {
	return (
		<ClayTable.Row>
			<ClayTable.Cell>
				<div className="projects-table-row-name-container">
					<img
						className="projects-table-row-name-icon"
						src={liferayIcon}
					/>

					<span className="projects-table-row-name-text">
						{projectName}
					</span>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<div className="projects-table-row-created-by-container">
					<span className="projects-table-row-created-by-name">
						{author}
					</span>

					<span className="projects-table-row-created-by-date">
						{createdAt}
					</span>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<Tag label="Fully Managed" />
			</ClayTable.Cell>

			<ClayTable.Cell>
				<span className="projects-table-row-end-date">{endDate}</span>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<div className="projects-table-row-provisioning-container">
					<img
						className={classNames(
							'projects-table-row-provisioning-icon-pending',
							{
								'projects-table-row-provisioning-icon-complete':
									status === 'Complete',
							}
						)}
						src={circleFillIcon}
					/>

					<span className="projects-table-row-provisioning-text">
						{status}
					</span>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<DropDown
					filterKey="name"
					trigger={
						<button className="projects-table-row-project-button-container">
							<span className="projects-table-row-project-button-text">
								Manage
							</span>

							<img
								className="projects-table-row-project-button-icon"
								src={arrowDownIcon}
							/>
						</button>
					}
				>
					<DropDown.ItemList>
						<DropDown.Group>
							<DropDown.Item key="Test" onClick={() => {}}>
								<span className="projects-table-row-dropdown-item-text">
									Go to DXP
								</span>
							</DropDown.Item>

							<DropDown.Item key="Test" onClick={() => {}}>
								<span className="projects-table-row-dropdown-item-text">
									Go to Console
								</span>
							</DropDown.Item>

							<DropDown.Item key="Test" onClick={() => {}}>
								<span className="projects-table-row-dropdown-item-text">
									Go to Git Repo
								</span>
							</DropDown.Item>

							<DropDown.Divider />

							<DropDown.Item key="Test" onClick={() => {}}>
								<span className="projects-table-row-dropdown-item-text-red">
									Delete Project
								</span>
							</DropDown.Item>
						</DropDown.Group>
					</DropDown.ItemList>
				</DropDown>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}
