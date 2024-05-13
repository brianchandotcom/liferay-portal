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
	deleteJenkinsCohortById,
	getJenkinsCohortById,
} from '../../objects/jenkins/JenkinsUtil';
import {toLocaleString} from '../../services/DateUtil';

function JenkinsMasters({jenkinsCohort}) {
	if (!jenkinsCohort?.jenkinsServers) {
		return <div>Loading...</div>;
	}

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle="Jenkins Servers"
			displayType="secondary"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>
				<Jethr0Table>
					<thead>
						<tr>
							<th>ID</th>
							<th>Name</th>
							<th>Jenkins Nodes</th>
							<th>Create Date</th>
							<th>Modified Date</th>
						</tr>
					</thead>
					<tbody>
						{jenkinsCohort.jenkinsServers?.map((jenkinsServer) => {
							return (
								<tr key={jenkinsServer.id}>
									<th className="font-weight-semi-bold">
										<Link
											title={jenkinsServer.id}
											to={
												'/jenkins-servers/' +
												jenkinsServer.id
											}
										>
											{jenkinsServer.id}
										</Link>
									</th>
									<td>
										<a href={jenkinsServer.url}>
											{jenkinsServer.name}
										</a>
									</td>
									<td>{jenkinsServer.jenkinsNodeCount}</td>
									<td>
										{toLocaleString(
											jenkinsServer.dateCreated
										)}
									</td>
									<td>
										{toLocaleString(
											jenkinsServer.dateModified
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

function JenkinsCohortInformation({jenkinsCohort}) {
	if (!jenkinsCohort) {
		return (
			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle="Jenkins Cohort Information"
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
			displayTitle="Jenkins Cohort Information"
			displayType="secondary"
		>
			<ClayPanel.Body>
				<Jethr0InformationField
					fieldLabel="Jenkins Cohort Name"
					fieldType="STRING"
					fieldValue={jenkinsCohort.name}
				/>

				<Jethr0InformationField
					fieldLabel="Jenkins Cohort ID"
					fieldType="STRING"
					fieldValue={jenkinsCohort.id}
				/>

				<Jethr0InformationField
					fieldLabel="Create Date"
					fieldType="DATE"
					fieldValue={jenkinsCohort.dateCreated}
				/>

				<Jethr0InformationField
					fieldLabel="Modified Date"
					fieldType="DATE"
					fieldValue={jenkinsCohort.dateModified}
				/>
			</ClayPanel.Body>
		</ClayPanel>
	);
}

function JenkinsCohortPage() {
	const {id} = useParams();
	const [jenkinsCohort, setJenkinsCohort] = useState(null);

	if (!jenkinsCohort) {
		getJenkinsCohortById({id, setJenkinsCohort});
	}

	if (!jenkinsCohort) {
		return (
			<ClayLayout.Container>
				<Jethr0Card>
					<Jethr0NavigationBar active="Jenkins" />

					<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

					<Jethr0ContainerFluid>
						<ClayLayout.Row justify="between">
							<Heading level={3} weight="lighter">
								{'Jenkins Cohort #' + id}
							</Heading>
						</ClayLayout.Row>
					</Jethr0ContainerFluid>
				</Jethr0Card>
			</ClayLayout.Container>
		);
	}

	function redirectToJenkinsCohortPage() {
		window.location.replace('/#/jenkins-cohorts/');
	}

	let jenkinsCohortName = 'Jenkins Cohort #' + id;

	if (jenkinsCohort) {
		jenkinsCohortName = jenkinsCohort.name;
	}

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/jenkins-cohorts', name: 'Jenkins'},
		{active: true, link: '/jenkins-cohorts/' + id, name: jenkinsCohortName},
	];

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Jenkins" />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Jethr0ContainerFluid>
					<ClayLayout.Row justify="between">
						<Heading level={3} weight="lighter">
							{jenkinsCohortName}
						</Heading>

						<Jethr0ButtonsRow
							buttons={[
								{
									link: `/jenkins-cohorts/${id}/create-server`,
									title: 'Create Jenkins Server',
								},
								{
									onClick: () => {
										deleteJenkinsCohortById({
											id,
											redirect: redirectToJenkinsCohortPage,
										});
									},
									title: 'Delete',
								},
							]}
						/>
					</ClayLayout.Row>
				</Jethr0ContainerFluid>

				<JenkinsCohortInformation jenkinsCohort={jenkinsCohort} />

				<JenkinsMasters jenkinsCohort={jenkinsCohort} />
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default JenkinsCohortPage;
