/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import RadioCardList from '../../../../../components/RadioCardList/RadioCardList';
import {ConsoleUserProject} from '../../../../../services/oauth/types';
import {convertSize} from '../../../../../utils/filesize';
import {productPurchaseStore} from '../../../store/AppPurchaseStore';

const getResourceSummary = (project: ConsoleUserProject) => {
	const cpu =
		project.rootProjectPlanUsage.cpu.limit -
		project.rootProjectPlanUsage.cpu.used;

	const environment = project.environments.length;

	const memory = convertSize(
		project.rootProjectPlanUsage.memory.limit -
			project.rootProjectPlanUsage.memory.used,
		'MB',
		'GB'
	);

	return `${environment} Environments, ${cpu} CPUs, ${memory} GB Ram`;
};

type CloudProjectsProps = {
	project: ConsoleUserProject;
	projects: ConsoleUserProject[];
};

const CloudProjects: React.FC<CloudProjectsProps> = ({project, projects}) => (
	<RadioCardList
		contentList={projects.map((cloudProject, index) => ({
			fullTitle: true,
			id: index,
			selected: cloudProject.rootProjectId === project?.rootProjectId,
			title: (
				<div>
					<div className="h5 m-0">
						{cloudProject.rootProjectId.toUpperCase()}
					</div>

					<small className="text-nowrap">
						{getResourceSummary(cloudProject)}
					</small>
				</div>
			),
			value: cloudProject,
		}))}
		leftRadio
		onSelect={(radioOption: RadioOption<ConsoleUserProject>) =>
			productPurchaseStore.send({
				project: radioOption.value,
				type: 'setProject',
			})
		}
	/>
);

export default CloudProjects;
