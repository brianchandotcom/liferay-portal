import DropDown from '@clayui/drop-down';
import ClayTable from '@clayui/table';

import arrowDownIcon from '../../assets/icons/arrow-down.svg';
import circleFillIcon from '../../assets/icons/circle_fill.svg';
import liferayIcon from '../../assets/icons/liferay-icon.svg';
import {Tag} from '../../components/Tag/Tag';

import './ProjectsTableRow.scss';

export function ProjectsTableRow() {
	return (
		<ClayTable.Row>
			<ClayTable.Cell>
				<div className="projects-table-row-name-container">
					<img
						className="projects-table-row-name-icon"
						src={liferayIcon}
					/>

					<span className="projects-table-row-name-text">
						newportal123
					</span>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<div className="projects-table-row-created-by-container">
					<span className="projects-table-row-created-by-name">
						Don Matsumae
					</span>

					<span className="projects-table-row-created-by-date">
						Sep 18, 2023
					</span>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<Tag label="Fully Managed" />
			</ClayTable.Cell>

			<ClayTable.Cell>
				<span className="projects-table-row-end-date">
					Mar 16, 2024
				</span>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<div className="projects-table-row-provisioning-container">
					<img
						className="projects-table-row-provisioning-icon"
						src={circleFillIcon}
					/>

					<span className="projects-table-row-provisioning-text">
						Complete
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
