/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useState} from 'react';
import {Link} from 'react-router-dom';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0ButtonsRow from '../../components/Jethr0ButtonsRow/Jethr0ButtonsRow';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0ContainerFluid from '../../components/Jethr0ContainerFluid/Jethr0ContainerFluid';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import Jethr0Table from '../../components/Jethr0Table/Jethr0Table';
import {getJenkinsServersPage} from '../../objects/jenkins/JenkinsUtil';
import {toLocaleString} from '../../services/DateUtil';

function JenkinsServersPage() {
	const [jenkinsServersPage, setJenkinsServersPage] = useState(null);

	if (!jenkinsServersPage) {
		getJenkinsServersPage({setJenkinsServersPage});
	}

	if (!jenkinsServersPage) {
		return <div>Loading...</div>;
	}

	function setActiveDelta({activeDelta, jenkinsServersPage}) {
		getJenkinsServersPage({
			page: jenkinsServersPage.page,
			pageSize: activeDelta,
			setJenkinsServersPage,
		});
	}

	function setActivePage({activePage, jenkinsServersPage}) {
		getJenkinsServersPage({
			page: activePage,
			pageSize: jenkinsServersPage.pageSize,
			setJenkinsServersPage,
		});
	}

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: true, link: '/jenkins-cohorts', name: 'Jenkins'},
	];

	const deltas = [
		{
			label: 25,
		},
		{
			label: 50,
		},
		{
			label: 100,
		},
	];

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Jenkins" />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Jethr0ContainerFluid>
					<ClayLayout.Row justify="between">
						<Heading level={3} weight="lighter">
							Jenkins Servers
						</Heading>
						<Jethr0ButtonsRow
							buttons={[
								{
									link: '/jenkins-servers/create',
									title: 'Create Jenkins Server',
								},
							]}
						/>
					</ClayLayout.Row>
				</Jethr0ContainerFluid>

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
						{jenkinsServersPage?.jenkinsServers.map(
							(jenkinsServer) => {
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
										<td>{jenkinsServer.name}</td>
										<td>
											{jenkinsServer.jenkinsNodeCount}
										</td>
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
							}
						)}
					</tbody>
				</Jethr0Table>
			</Jethr0Card>

			{jenkinsServersPage && (
				<ClayPaginationBarWithBasicItems
					activeDelta={jenkinsServersPage.pageSize}
					defaultActive={jenkinsServersPage.page}
					deltas={deltas}
					ellipsisBuffer={3}
					onActiveChange={(activePage) => {
						setActivePage({activePage, jenkinsServersPage});
					}}
					onDeltaChange={(activeDelta) => {
						setActiveDelta({activeDelta, jenkinsServersPage});
					}}
					showDeltasDropDown={true}
					totalItems={jenkinsServersPage.totalCount}
				/>
			)}
		</ClayLayout.Container>
	);
}

export default JenkinsServersPage;
