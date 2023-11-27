/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';

import {toLocaleString} from '../../services/DateUtil';
import Jethr0Table from '../Jethr0Table/Jethr0Table';

function BuildInformation({build}) {
	if (!build) {
		return (
			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle="Build Information"
				displayType="secondary"
			>
				<ClayPanel.Body>Loading...</ClayPanel.Body>
			</ClayPanel>
		);
	}

	const parameters = JSON.parse(build.parameters);

	return (
		<>
			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle="Build Information"
				displayType="secondary"
			>
				<ClayPanel.Body>
					Build Name: {build.name}
					<br />
					Build ID: {build.id}
					<br />
					Build State: {build.state.name}
					<br />
					Create Date: {toLocaleString(build.dateCreated)}
					<br />
					Modified Date: {toLocaleString(build.dateModified)}
					<br />
					Jenkins Job Name: {build.jenkinsJobName}
				</ClayPanel.Body>
			</ClayPanel>
			{parameters && (
				<ClayPanel
					collapsable
					displayTitle="Build Parameters"
					displayType="secondary"
					showCollapseIcon={true}
				>
					<ClayPanel.Body>
						<Jethr0Table>
							<thead>
								<tr>
									<th>Name</th>
									<th>Value</th>
								</tr>
							</thead>
							<tbody>
								{parameters.map((parameter) => {
									return (
										<tr key={parameter.name}>
											<td title={parameter.name}>
												{parameter.name}
											</td>
											<td>{parameter.value}</td>
										</tr>
									);
								})}
							</tbody>
						</Jethr0Table>
					</ClayPanel.Body>
				</ClayPanel>
			)}
		</>
	);
}

export default BuildInformation;
