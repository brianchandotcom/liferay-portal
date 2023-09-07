/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import ClayLayout from '@clayui/layout';
import {Heading} from '@clayui/core';
import {useState} from 'react';
import {useParams} from 'react-router-dom';

import Breadcrumbs from '../../components/Breadcrumbs/Breadcrumbs';
import BuildTable from '../../components/BuildTable/BuildTable';
import setSpringBootData from '../../services/setSpringBootData';

function JobPage() {
	const {id} = useParams();
	let [job, setJob] = useState(null);
	let [jobBuilds, setJobBuilds] = useState(null);

	setSpringBootData({
		setData: setJob,
		urlPath: '/jobs/' + id
	});

	setSpringBootData({
		setData: setJobBuilds,
		timeout: 2500,
		urlPath: '/jobs/builds/' + jobId
	});

	let jobName = 'Job #' + id;

	if (job) {
		jobName = job.name;
	}

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/jobs', name: 'Jobs'},
		{active: true, link: '/jobs/{id}', name: jobName},
	];

	return (
		<ClayLayout.Container>
			<ClayCard className="jethr0-card">
				<Breadcrumbs breadcrumbs={breadcrumbs} />
				<Heading level={3} weight="lighter">{jobName}</Heading>
				<JobInformation job={job} />
				<BuildTable builds={jobBuilds} />
			</ClayCard>
		</ClayLayout.Container>
	);
}

function JobInformation({job}) {
	let jobInformation = (<div>Loading...</div>);

	if (job) {
		jobInformation = (<ClayPanel.Body>
			Job ID: {job.id}
			<br />
			Create Date: {job.dateCreated}
			<br />
			Modified Date: {job.dateModified}
			<br />
			Job State: {job.state.name}
			<br />
			Job Type: {job.type.name}
		</ClayPanel.Body>);
	}

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle="Job Information"
			displayType="secondary"
		>
			{jobInformation}
		</ClayPanel>
	);
}

export default JobPage;
