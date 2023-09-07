/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';

function JobInformation({job}) {
	if (!job) {
		return (
			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle="Job Information"
				displayType="secondary"
			>
				<ClayPanel.Body>Loading...</ClayPanel.Body>
			</ClayPanel>
		);
	}

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle="Job Information"
			displayType="secondary"
		>
			<ClayPanel.Body>
				Job Name: {job.name}
				<br />
				Job ID: {job.id}
				<br />
				Job State: {job.state.name}
				<br />
				Job Type: {job.type.name}
				<br />
				Create Date: {job.dateCreated}
				<br />
				Modified Date: {job.dateModified}
			</ClayPanel.Body>
		</ClayPanel>
	);
}

export default JobInformation;
