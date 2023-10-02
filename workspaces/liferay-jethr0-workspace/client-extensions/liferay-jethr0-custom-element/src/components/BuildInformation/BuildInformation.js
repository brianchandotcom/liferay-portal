/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';

import {toLocaleString} from '../../services/DateUtil';

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

	return (
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
			</ClayPanel.Body>
		</ClayPanel>
	);
}

export default BuildInformation;
