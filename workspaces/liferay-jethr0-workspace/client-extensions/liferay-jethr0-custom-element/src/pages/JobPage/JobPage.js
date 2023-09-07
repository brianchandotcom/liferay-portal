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
import JobBuilds from '../../components/JobBuilds/JobBuilds';
import JobInformation from '../../components/JobInformation/JobInformation';
import setSpringBootData from '../../services/setSpringBootData';

function JobPage() {
	const {id} = useParams();
	let [job, setJob] = useState(null);

	setSpringBootData({
		setData: setJob,
		urlPath: '/jobs/' + id
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
				<JobBuilds jobId={id} />
			</ClayCard>
		</ClayLayout.Container>
	);
}

export default JobPage;
