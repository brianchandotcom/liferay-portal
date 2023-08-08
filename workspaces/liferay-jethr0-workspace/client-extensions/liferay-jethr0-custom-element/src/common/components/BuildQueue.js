/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
		<div>
			<table border="1">
				<tr>
					<th>ID</th>

					<th>Name</th>

					<th>Priority</th>

					<th>Create Date</th>

					<th>Start Date</th>

					<th>State</th>

					<th>Type</th>

					<th>Queued</th>

					<th>Running</th>

					<th>Completed</th>

					<th>Total</th>
				</tr>

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
						<tr key={project.id}>
							<td>{project.id}</td>

							<td>{project.name}</td>

							<td>{project.priority}</td>

							<td>{project.dateCreated}</td>

							<td>{project.startDate}</td>

							<td>{project.state.name}</td>

							<td>{project.type.name}</td>

							<td>{queuedBuilds}</td>

							<td>{runningBuilds}</td>

							<td>{completedBuilds}</td>

							<td>{totalBuilds}</td>
						</tr>
					);
				})}
			</table>
		</div>
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
