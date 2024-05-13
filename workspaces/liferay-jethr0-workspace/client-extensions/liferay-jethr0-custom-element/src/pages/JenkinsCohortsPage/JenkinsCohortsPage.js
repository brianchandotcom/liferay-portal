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
import {getJenkinsCohortsPage} from '../../objects/jenkins/JenkinsUtil';
import {toLocaleString} from '../../services/DateUtil';

function JenkinsCohortsPage() {
	const [jenkinsCohortsPage, setJenkinsCohortsPage] = useState(null);

	if (!jenkinsCohortsPage) {
		getJenkinsCohortsPage({setJenkinsCohortsPage});
	}

	if (!jenkinsCohortsPage) {
		return <div>Loading...</div>;
	}

	function setActiveDelta({activeDelta, jenkinsCohortsPage}) {
		getJenkinsCohortsPage({
			page: jenkinsCohortsPage.page,
			pageSize: activeDelta,
			setJenkinsCohortsPage,
		});
	}

	function setActivePage({activePage, jenkinsCohortsPage}) {
		getJenkinsCohortsPage({
			page: activePage,
			pageSize: jenkinsCohortsPage.pageSize,
			setJenkinsCohortsPage,
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
							Jenkins Cohorts
						</Heading>
						<Jethr0ButtonsRow
							buttons={[
								{
									link: '/jenkins-cohorts/create',
									title: 'Create Jenkins Cohort',
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
							<th>Jenkins Servers</th>
							<th>Create Date</th>
							<th>Modified Date</th>
						</tr>
					</thead>
					<tbody>
						{jenkinsCohortsPage?.jenkinsCohorts.map(
							(jenkinsCohort) => {
								return (
									<tr key={jenkinsCohort.id}>
										<th className="font-weight-semi-bold">
											<Link
												title={jenkinsCohort.id}
												to={
													'/jenkins-cohorts/' +
													jenkinsCohort.id
												}
											>
												{jenkinsCohort.id}
											</Link>
										</th>
										<td>{jenkinsCohort.name}</td>
										<td>
											{jenkinsCohort.jenkinsServerCount}
										</td>
										<td>
											{toLocaleString(
												jenkinsCohort.dateCreated
											)}
										</td>
										<td>
											{toLocaleString(
												jenkinsCohort.dateModified
											)}
										</td>
									</tr>
								);
							}
						)}
					</tbody>
				</Jethr0Table>
			</Jethr0Card>

			{jenkinsCohortsPage && (
				<ClayPaginationBarWithBasicItems
					activeDelta={jenkinsCohortsPage.pageSize}
					defaultActive={jenkinsCohortsPage.page}
					deltas={deltas}
					ellipsisBuffer={3}
					onActiveChange={(activePage) => {
						setActivePage({activePage, jenkinsCohortsPage});
					}}
					onDeltaChange={(activeDelta) => {
						setActiveDelta({activeDelta, jenkinsCohortsPage});
					}}
					showDeltasDropDown={true}
					totalItems={jenkinsCohortsPage.totalCount}
				/>
			)}
		</ClayLayout.Container>
	);
}

export default JenkinsCohortsPage;
