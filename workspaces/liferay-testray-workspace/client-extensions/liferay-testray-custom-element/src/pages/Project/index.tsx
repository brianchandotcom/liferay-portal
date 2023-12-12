/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Container from '~/components/Layout/Container';
import ListView from '~/components/ListView';
import {useHeader} from '~/hooks';
import i18n from '~/i18n';

import useProjectActions from './useProjectActions';

type ProjectsProps = {
	PageContainer?: React.FC;
};

const Projects: React.FC<ProjectsProps> = ({PageContainer = Container}) => {
	const {actions, navigate} = useProjectActions();

	useHeader({
		dropdown: [],
		headerActions: {actions: []},
		heading: [
			{
				category: i18n.translate('project'),
				title: i18n.translate('project-directory'),
			},
		],
		icon: 'polls',
	});

	return (
		<PageContainer>
			<ListView
				managementToolbarProps={{
					addButton: () => navigate('/project/create'),
					display: {columns: false},
					title: i18n.translate('projects'),
				}}
				resource="/projects?fields=actions,description,id,name"
				tableProps={{
					actions,
					columns: [
						{
							clickable: true,
							key: 'name',
							size: 'lg',
							sorteable: true,
							value: i18n.translate('project'),
						},
						{
							key: 'description',
							size: 'lg',
							value: i18n.translate('description'),
						},
					],
					navigateTo: (project) => `/project/${project.id}/routines`,
				}}
			/>
		</PageContainer>
	);
};

export default Projects;
