/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import JobQueue from '../../components/JobQueue/JobQueue';

function Home() {
	const breadcrumbs = [{active: true, link: '/', name: 'Home'}];

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Home" />
				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />
				<Heading level={3} weight="lighter">
					Job Queue
				</Heading>
				<JobQueue />
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default Home;
