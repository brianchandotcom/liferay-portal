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

import getEntities from '../services/entity.js';

function BuildQueue() {
	const [data, setData] = React.useState(null);

	getEntities('projects', '((state eq \'opened\') or (state eq \'queued\') or (state eq \'running\'))')
		.then((data) => {
			const projects = [];

			data.items.map(project => {
				projects.push(project);
			})

			setData(projects)
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
				</tr>
				{
					data.map(project => {
						return (
							<tr>
								<td>{project.id}</td>
								<td>{project.name}</td>
								<td>{project.priority}</td>
								<td>{project.state.name}</td>
								<td>{project.type.name}</td>
							</tr>
						)}
					)
				}
			</table>
		</div>
	);
}

export default BuildQueue;
