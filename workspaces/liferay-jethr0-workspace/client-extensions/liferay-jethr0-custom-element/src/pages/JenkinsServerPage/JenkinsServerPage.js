/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import {useState} from 'react';
import {Link, useParams} from 'react-router-dom';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0ButtonsRow from '../../components/Jethr0ButtonsRow/Jethr0ButtonsRow';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0ContainerFluid from '../../components/Jethr0ContainerFluid/Jethr0ContainerFluid';
import Jethr0InformationField from '../../components/Jethr0InformationField/Jethr0InformationField';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import Jethr0Table from '../../components/Jethr0Table/Jethr0Table';
import {
	deleteJenkinsServerById,
	getJenkinsServerById,
} from '../../objects/jenkins/JenkinsUtil';
import {toLocaleString} from '../../services/DateUtil';

function JenkinsNodes({jenkinsServer}) {
	if (!jenkinsServer?.jenkinsNodes) {
		return <div>Loading...</div>;
	}

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle="Jenkins Nodes"
			displayType="secondary"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>
				<Jethr0Table>
					<thead>
						<tr>
							<th>ID</th>
							<th>Name</th>
							<th>Create Date</th>
							<th>Modified Date</th>
						</tr>
					</thead>
					<tbody>
						{jenkinsServer?.jenkinsNodes?.map((jenkinsNode) => {
							return (
								<tr key={jenkinsNode.id}>
									<th className="font-weight-semi-bold">
										<Link
											title={jenkinsNode.id}
											to={
												'/jenkins-server/' +
												jenkinsNode.id
											}
										>
											{jenkinsNode.id}
										</Link>
									</th>
									<td>
										<a href={jenkinsNode.url}>
											{jenkinsNode.name}
										</a>
									</td>
									<td>
										{toLocaleString(
											jenkinsNode.dateCreated
										)}
									</td>
									<td>
										{toLocaleString(
											jenkinsNode.dateModified
										)}
									</td>
								</tr>
							);
						})}
					</tbody>
				</Jethr0Table>
			</ClayPanel.Body>
		</ClayPanel>
	);
}

function JenkinsServerInformation({jenkinsServer}) {
	if (!jenkinsServer) {
		return (
			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle="Jenkins Server Information"
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
			displayTitle="Jenkins Server Information"
			displayType="secondary"
		>
			<ClayPanel.Body>
				<Jethr0InformationField
					fieldLabel="Jenkins Server ID"
					fieldType="STRING"
					fieldValue={jenkinsServer.id}
				/>

				<Jethr0InformationField
					fieldLabel="Jenkins Server Name"
					fieldType="STRING"
					fieldValue={jenkinsServer.name}
				/>

				<Jethr0InformationField
					fieldLabel="Jenkins Server URL"
					fieldType="URL"
					fieldValue={jenkinsServer.url}
				/>

				{jenkinsServer.jenkinsCohort && (
					<Jethr0InformationField
						fieldLabel="Jenkins Cohort"
						fieldType="URL"
						fieldURLValue={
							'/#/jenkins-cohorts/' +
							jenkinsServer.jenkinsCohort.id
						}
						fieldValue={jenkinsServer.jenkinsCohort.name}
					/>
				)}

				<Jethr0InformationField
					fieldLabel="Create Date"
					fieldType="DATE"
					fieldValue={jenkinsServer.dateCreated}
				/>

				<Jethr0InformationField
					fieldLabel="Modified Date"
					fieldType="DATE"
					fieldValue={jenkinsServer.dateModified}
				/>
			</ClayPanel.Body>
		</ClayPanel>
	);
}

function JenkinsServerPage() {
	const {id} = useParams();
	const [jenkinsServer, setJenkinsServer] = useState(null);

	if (!jenkinsServer) {
		getJenkinsServerById({id, setJenkinsServer});
	}

	if (!jenkinsServer) {
		return (
			<ClayLayout.Container>
				<Jethr0Card>
					<Jethr0NavigationBar active="Jenkins" />

					<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

					<Jethr0ContainerFluid>
						<ClayLayout.Row justify="between">
							<Heading level={3} weight="lighter">
								{'Jenkins Server #' + id}
							</Heading>
						</ClayLayout.Row>
					</Jethr0ContainerFluid>
				</Jethr0Card>
			</ClayLayout.Container>
		);
	}

	function redirectToJenkinsServerPage() {
		if (jenkinsServer.jenkinsCohort) {
			window.location.replace(
				'/#/jenkins-cohorts/' + jenkinsServer.jenkinsCohort.id
			);
		}
		else {
			window.location.replace('/#/jenkins-servers/');
		}
	}

	let jenkinsServerName = 'Jenkins Server #' + id;

	if (jenkinsServer) {
		jenkinsServerName = jenkinsServer.name;
	}

	let breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/jenkins-cohorts', name: 'Jenkins'},
		{active: true, link: '/jenkins-servers/' + id, name: jenkinsServerName},
	];

	if (jenkinsServer.jenkinsCohort) {
		breadcrumbs = [
			{active: false, link: '/', name: 'Home'},
			{active: false, link: '/jenkins-cohorts', name: 'Jenkins'},
			{
				active: false,
				link: '/jenkins-cohorts/' + jenkinsServer.jenkinsCohort.id,
				name: jenkinsServer.jenkinsCohort.name,
			},
			{
				active: true,
				link: '/jenkins-servers/' + id,
				name: jenkinsServerName,
			},
		];
	}

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Jenkins" />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Jethr0ContainerFluid>
					<ClayLayout.Row justify="between">
						<Heading level={3} weight="lighter">
							{jenkinsServerName}
						</Heading>

						<Jethr0ButtonsRow
							buttons={[
								{
									onClick: () => {
										deleteJenkinsServerById({
											id,
											redirect: redirectToJenkinsServerPage,
										});
									},
									title: 'Delete',
								},
							]}
						/>
					</ClayLayout.Row>
				</Jethr0ContainerFluid>

				<JenkinsServerInformation jenkinsServer={jenkinsServer} />

				<JenkinsNodes jenkinsServer={jenkinsServer} />
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default JenkinsServerPage;
