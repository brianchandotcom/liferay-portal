/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import React from 'react';

import entities from '../services/entity.js';

function BuildQueue() {
	const [data, setData] = React.useState(null);

	projects()
		.then((projects) => {
			setData(projects);
		})
		.catch(() => setData(null));

	if (!data) {
		return <div>Loading...</div>;
	}

	return (
		<ClayTable borderedColumns responsiveSize="sm">
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell>ID</ClayTable.Cell>

					<ClayTable.Cell headingCell>Name</ClayTable.Cell>

					<ClayTable.Cell headingCell>Priority</ClayTable.Cell>

					<ClayTable.Cell headingCell>Create Date</ClayTable.Cell>

					<ClayTable.Cell headingCell>Start Date</ClayTable.Cell>

					<ClayTable.Cell headingCell>State</ClayTable.Cell>

					<ClayTable.Cell headingCell>Type</ClayTable.Cell>

					<ClayTable.Cell headingCell>Queued</ClayTable.Cell>

					<ClayTable.Cell headingCell>Running</ClayTable.Cell>

					<ClayTable.Cell headingCell>Completed</ClayTable.Cell>

					<ClayTable.Cell headingCell>Total</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>
			<ClayTable.Body>
				{data.map((project) => {
					let completedBuilds = 0;
					let queuedBuilds = 0;
					let runningBuilds = 0;
					let totalBuilds = 0;

					for (const build of project.builds) {
						if (build.state.key === 'completed') {
							completedBuilds++;
						}
						else if (build.state.key === 'running') {
							runningBuilds++;
						}
						else {
							queuedBuilds++;
						}

						totalBuilds++;
					}

					return (
						<ClayTable.Row key={project.id}>
							<ClayTable.Cell headingCell>
								{project.id}
							</ClayTable.Cell>

							<ClayTable.Cell>{project.name}</ClayTable.Cell>

							<ClayTable.Cell>{project.priority}</ClayTable.Cell>

							<ClayTable.Cell>
								{project.dateCreated}
							</ClayTable.Cell>

							<ClayTable.Cell>{project.startDate}</ClayTable.Cell>

							<ClayTable.Cell>
								{project.state.name}
							</ClayTable.Cell>

							<ClayTable.Cell>{project.type.name}</ClayTable.Cell>

							<ClayTable.Cell>{queuedBuilds}</ClayTable.Cell>

							<ClayTable.Cell>{runningBuilds}</ClayTable.Cell>

							<ClayTable.Cell>{completedBuilds}</ClayTable.Cell>

							<ClayTable.Cell>{totalBuilds}</ClayTable.Cell>
						</ClayTable.Row>
					);
				})}
			</ClayTable.Body>
		</ClayTable>
	);
}

const projects = async () => {
	const projects = [];

	await entities(
		'projects',
		"((state eq 'opened') or (state eq 'queued') or (state eq 'running'))",
		'position:asc'
	).then(async (data) => {
		for (const project of data.items) {
			await entities(
				'builds',
				"r_projectToBuilds_c_projectId eq '" + project.id + "'"
			).then((data) => {
				project.builds = data.items;
			});

			projects.push(project);
		}
	});

	return projects;
};

export default BuildQueue;
