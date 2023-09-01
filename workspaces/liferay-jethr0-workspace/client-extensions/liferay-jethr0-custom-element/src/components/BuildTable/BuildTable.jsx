/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import ClayTable from '@clayui/table';

function BuildTable({ builds }) {
	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle="Builds"
			displayType="secondary"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>
				<ClayTable borderedColumns responsiveSize="sm">
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell>ID</ClayTable.Cell>

							<ClayTable.Cell headingCell>Build Name</ClayTable.Cell>

							<ClayTable.Cell headingCell>Jenkins Job Name</ClayTable.Cell>

							<ClayTable.Cell headingCell>Create Date</ClayTable.Cell>

							<ClayTable.Cell headingCell>Build State</ClayTable.Cell>
						</ClayTable.Row>
					</ClayTable.Head>
					<ClayTable.Body>
						{builds && builds.map((build) => {
							return (
								<ClayTable.Row>
									<ClayTable.Cell headingCell>{build.id}</ClayTable.Cell>

									<ClayTable.Cell>{build.name}</ClayTable.Cell>

									<ClayTable.Cell>{build.jenkinsJobName}</ClayTable.Cell>

									<ClayTable.Cell>
										{build.dateCreated}
									</ClayTable.Cell>

									<ClayTable.Cell>{build.state.name}</ClayTable.Cell>
								</ClayTable.Row>
							);
						})}
					</ClayTable.Body>
				</ClayTable>
			</ClayPanel.Body>
		</ClayPanel>
	);
}

export default BuildTable;