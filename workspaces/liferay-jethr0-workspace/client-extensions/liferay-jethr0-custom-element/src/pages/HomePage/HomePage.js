/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import ClayLayout from '@clayui/layout';
import {Heading} from '@clayui/core';

import './HomePage.css';

import Breadcrumbs from '../../components/Breadcrumbs/Breadcrumbs';
import JobQueue from '../../components/JobQueue/JobQueue';

function Home() {
	const breadcrumbs = [
		{active:true, link: '/', name: 'Home'},
	];

	return (
		<ClayLayout.Container>
			<ClayCard className="jethr0-card">
				<Breadcrumbs breadcrumbs={breadcrumbs} />
				<Heading level={3} weight="lighter">Job Queue</Heading>
				<JobQueue />
			</ClayCard>
		</ClayLayout.Container>
	);
}

export default Home;
