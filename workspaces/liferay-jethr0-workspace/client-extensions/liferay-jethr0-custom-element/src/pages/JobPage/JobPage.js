/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import {useState} from 'react';
import {Link, useParams} from 'react-router-dom';

import './JobPage.css';
import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0ButtonsRow from '../../components/Jethr0ButtonsRow/Jethr0ButtonsRow';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import Jethr0Table from '../../components/Jethr0Table/Jethr0Table';
import {toLocaleString} from '../../services/DateUtil';
import {toDurationString} from '../../services/DurationUtil';
import postSpringBootData from '../../services/postSpringBootData';
import useSpringBootData from '../../services/useSpringBootData';

function JobBuilds({jobId}) {
	const [jobBuilds, setJobBuilds] = useState(null);

	useSpringBootData({
		setData: setJobBuilds,
		urlPath: '/jobs/' + jobId + '/builds',
	});

	if (!jobBuilds) {
		return <div>Loading...</div>;
	}

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle="Builds"
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
							<th>State</th>
							<th>Initial Build</th>
							<th>Jenkins Duration</th>
							<th>Jenkins Build</th>
						</tr>
					</thead>
					<tbody>
						{jobBuilds &&
							jobBuilds.map((jobBuild) => {
								return (
									<tr key={jobBuild.id}>
										<th className="font-weight-semi-bold">
											<Link
												title={jobBuild.id}
												to={'/builds/' + jobBuild.id}
											>
												{jobBuild.id}
											</Link>
										</th>
										<td>{jobBuild.name}</td>
										<td>
											{toLocaleString(
												jobBuild.dateCreated
											)}
										</td>
										<td>{jobBuild.state.name}</td>
										<td>
											{jobBuild.initialBuild.toString()}
										</td>
										<td>
											{toDurationString(
												jobBuild.latestDuration
											)}
										</td>
										<td>
											{jobBuild.latestJenkinsBuildURL ? (
												<a
													href={
														jobBuild.latestJenkinsBuildURL
													}
												>
													Latest Jenkins Build
												</a>
											) : (
												<div>-</div>
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
				Create Date: {toLocaleString(job.dateCreated)}
				<br />
				Modified Date: {toLocaleString(job.dateModified)}
				<br />
				Start Date: {toLocaleString(job.startDate)}
				{job.jenkinsGitHubURL && job.jenkinsGitHubURL !== null && (
					<>
						<br />
						Jenkins GitHub URL:{' '}
						<a href={job.jenkinsGitHubURL}>
							{job.jenkinsGitHubURL}
						</a>
					</>
				)}
				{job.portalPullRequestURL && (
					<>
						<br />
						Portal Pull Request URL:{' '}
						<a href={job.portalPullRequestURL}>
							{job.portalPullRequestURL}
						</a>
					</>
				)}
			</ClayPanel.Body>
		</ClayPanel>
	);
}

function JobPage() {
	const {id} = useParams();
	const [job, setJob] = useState(null);

	useSpringBootData({
		setData: setJob,
		urlPath: '/jobs/' + id,
	});

	function redirectToJobsPage() {
		window.location.replace('/#/jobs');
	}

	let jobName = 'Job #' + id;

	if (job) {
		jobName = job.name;
	}

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/jobs', name: 'Jobs'},
		{active: true, link: '/jobs/' + id, name: jobName},
	];

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Jobs" />
				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />
				<ClayLayout.ContainerFluid className="jethr0-job-page-menu">
					<ClayLayout.Row justify="between">
						<Heading level={3} weight="lighter">
							{jobName}
						</Heading>
						<Jethr0ButtonsRow
							buttons={[
								{
									onClick: () => {
										postSpringBootData({
											redirect: redirectToJobsPage,
											urlPath: '/jobs/delete/' + id,
										});
									},
									title: 'Delete',
								},
							]}
						/>
					</ClayLayout.Row>
				</ClayLayout.ContainerFluid>
				<JobInformation job={job} />
				<JobBuilds jobId={id} />
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default JobPage;
