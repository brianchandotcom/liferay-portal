/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import ClayLayout from '@clayui/layout';
import ClayPanel from "@clayui/panel";
import {Heading} from '@clayui/core';
import {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';

import Breadcrumbs from '../../components/Breadcrumbs/Breadcrumbs';
import BuildTable from '../../components/BuildTable/BuildTable';

let oAuth2Client;

try {
	oAuth2Client = Liferay.OAuth2Client.FromUserAgentApplication(
		'liferay-jethr0-etc-spring-boot-oauth-application-user-agent'
	);
}
catch (error) {
	console.error(error);
}

function ProjectPage() {
	const [project, setProject] = useState(null);
	const {id} = useParams();

	useEffect(() => {
		oAuth2Client
			?.fetch('/projects/' + id)
			.then((response) => response.text())
			.then((project) => {
				setProject(JSON.parse(project));
			})
			// eslint-disable-next-line no-console
			.catch((error) => console.log(error));
	}, []);

	let projectBuilds = [];
	let projectName = 'Project #' + id;

	if (project) {
		projectBuilds = project.builds;
		projectName = project.name;
	}

	const breadcrumbs = [
		{ active: false, link: '/', name: 'Home' },
		{ active: false, link: '/projects', name: 'Projects' },
		{ active: true, link: '/projects/{id}', name: projectName },
	];

	return (
		<ClayLayout.Container>
			<ClayCard className="jethr0-card">
				<Breadcrumbs breadcrumbs={breadcrumbs} />
				<Heading level={3} weight="lighter">{projectName}</Heading>
				<ProjectInformation project={project} />
				<BuildTable builds={projectBuilds} />
			</ClayCard>
		</ClayLayout.Container>
	);
}

function ProjectInformation({project}) {
	let projectInformation = (<div>Loading...</div>);

	if (project) {
		projectInformation = (<ClayPanel.Body>
			Project ID: {project.id}
			<br />
			Create Date: {project.dateCreated}
			<br />
			Modified Date: {project.dateModified}
			<br />
			Project State: {project.state.name}
			<br />
			Project Type: {project.type.name}
		</ClayPanel.Body>);
	}

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle="Project Information"
			displayType="secondary"
		>
			{projectInformation}
		</ClayPanel>
	);
}

export default ProjectPage;
