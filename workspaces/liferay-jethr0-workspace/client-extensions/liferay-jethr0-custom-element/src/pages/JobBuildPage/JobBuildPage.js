/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import {useState} from 'react';
import {useParams} from 'react-router-dom';

import BuildInformation from '../../components/BuildInformation/BuildInformation';
import BuildRuns from '../../components/BuildRuns/BuildRuns';
import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import useSpringBootData from '../../services/useSpringBootData';

function JobBuildPage() {
	const {id} = useParams();
	const [build, setBuild] = useState(null);

	useSpringBootData({
		setData: setBuild,
		urlPath: '/jobs/build/' + id,
	});

	let buildName = 'Build #' + id;
	let jobId = 0;
	let jobName = 'Unknown Job';

	if (build) {
		buildName = build.name;

		if (build.job) {
			jobId = build.job.id;
			jobName = build.job.name;
		}
	}

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/jobs', name: 'Jobs'},
		{active: false, link: '/jobs/' + jobId, name: jobName},
		{active: true, link: '/jobs/' + jobId + '/' + id, name: buildName},
	];

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Jobs" />
				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />
				<Heading level={3} weight="lighter">
					{buildName}
				</Heading>
				<BuildInformation build={build} />
				<BuildRuns buildId={id} />
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default JobBuildPage;
