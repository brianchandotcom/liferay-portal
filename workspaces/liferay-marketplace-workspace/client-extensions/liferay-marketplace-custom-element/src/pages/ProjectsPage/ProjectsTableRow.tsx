/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
