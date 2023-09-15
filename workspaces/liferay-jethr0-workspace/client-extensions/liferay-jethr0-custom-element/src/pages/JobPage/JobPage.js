/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import {useState} from 'react';
import {useParams} from 'react-router-dom';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import JobBuilds from '../../components/JobBuilds/JobBuilds';
import JobInformation from '../../components/JobInformation/JobInformation';
import useSpringBootData from '../../services/useSpringBootData';

function JobPage() {
	const {id} = useParams();
	const [job, setJob] = useState(null);

	useSpringBootData({
		setData: setJob,
		urlPath: '/jobs/' + id,
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
			<Jethr0Card>
				<Jethr0NavigationBar active="Jobs" />
				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />
				<Heading level={3} weight="lighter">
					{jobName}
				</Heading>
				<JobInformation job={job} />
				<JobBuilds jobId={id} />
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default JobPage;
