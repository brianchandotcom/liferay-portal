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
import Jobs from '../../components/Jobs/Jobs';
import useSpringBootData from '../../services/useSpringBootData';

function JobsPage() {
	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/jobs', name: 'Jobs'},
	];

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />
				<Heading level={3} weight="lighter">Jobs</Heading>
				<Jobs />
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default JobsPage;
