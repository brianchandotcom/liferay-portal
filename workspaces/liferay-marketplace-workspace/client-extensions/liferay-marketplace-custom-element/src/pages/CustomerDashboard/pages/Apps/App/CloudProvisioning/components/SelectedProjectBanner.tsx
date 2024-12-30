/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../../../../i18n';
import {convertSize} from '../../../../../../../utils/filesize';
import {ConsoleUserProjectWithExtension} from '../pages/CloudProvisioningOutlet';

const SelectedProjectBanner: React.FC<{
	project: ConsoleUserProjectWithExtension;
}> = ({project}) => (
	<div>
		<hr />

		<div className="align-items-center d-flex justify-content-between">
			<small className="font-weight-bold">
				{i18n.translate('project-selected')}
			</small>

			<span className="align-items-end d-flex flex-column">
				<small className="font-weight-bold m-0">
					{project?.rootProjectId.toUpperCase()}
				</small>

				<small className="subscription-banner-text text-nowrap">
					{`${project?.environments.length} environments, ${project?.rootProjectPlanUsage.cpu.free} CPUs, ${convertSize(
						project?.rootProjectPlanUsage.memory.free,
						'MB',
						'GB'
					)} GB RAM`}
				</small>
			</span>
		</div>
	</div>
);

export default SelectedProjectBanner;
