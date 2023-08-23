/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import React from 'react';

import {Liferay} from '../services/liferay/liferay';

let oAuth2Client;

try {
	oAuth2Client = Liferay.OAuth2Client.FromUserAgentApplication(
		'liferay-jethr0-etc-spring-boot-oauth-application-user-agent'
	);
}
catch (error) {
	console.error(error);
}

function BuildQueue() {
	const [data, setData] = React.useState(null);

	React.useEffect(() => {
		oAuth2Client
			?.fetch('/build-queue')
			.then((response) => response.text())
			.then((data) => {
				setData(JSON.parse(data));
			})
			.catch((error) => console.log(error));
	}, []);

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

							<ClayTable.Cell>{project.queuedBuilds}</ClayTable.Cell>

							<ClayTable.Cell>{project.runningBuilds}</ClayTable.Cell>

							<ClayTable.Cell>{project.completedBuilds}</ClayTable.Cell>

							<ClayTable.Cell>{project.totalBuilds}</ClayTable.Cell>
						</ClayTable.Row>
					);
				})}
			</ClayTable.Body>
		</ClayTable>
	);
}

export default BuildQueue;
