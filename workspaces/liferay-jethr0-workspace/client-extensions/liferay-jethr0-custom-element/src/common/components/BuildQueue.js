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

import React from 'react';

import entities from '../services/entity.js';

function BuildQueue() {
	const [data, setData] = React.useState(null);

	projects()
	.then((projects) => {
		setData(projects);
	});

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
					<th>State</th>
					<th>Type</th>
					<th>Running</th>
					<th>Completed</th>
					<th>Total</th>
				</tr>
				{
					data.map(project => {
						var runningBuilds = 0;
						var completedBuilds = 0;
						var totalBuilds = 0;

						for (const build of project.builds) {
							if (build.state.name == 'completed') {
								completedBuilds++;
							}
							else {
								runningBuilds++;
							}

							totalBuilds++;
						}

						return (
							<tr>
								<td>{project.id}</td>
								<td>{project.name}</td>
								<td>{project.priority}</td>
								<td>{project.state.name}</td>
								<td>{project.type.name}</td>
								<td>{runningBuilds}</td>
								<td>{completedBuilds}</td>
								<td>{totalBuilds}</td>
							</tr>
						)}
					)
				}
			</table>
		</div>
	);
}

const projects = async () => {
    const projects = [];

	await entities('projects', '((state eq \'opened\') or (state eq \'queued\') or (state eq \'running\'))')
	.then(async (data) => {
		for (const project of data.items) {
			await entities('builds', 'r_projectToBuilds_c_projectId eq \'' + project.id + '\'')
			.then((data) => {
				project.builds = data.items;
			});

			projects.push(project);
		}
	});

	return projects;
};

export default BuildQueue;
