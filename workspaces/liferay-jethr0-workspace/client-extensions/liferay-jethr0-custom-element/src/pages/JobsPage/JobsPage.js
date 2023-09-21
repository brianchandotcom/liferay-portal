/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';

import './JobsPage.css';
import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0ButtonsRow from '../../components/Jethr0ButtonsRow/Jethr0ButtonsRow';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import Jobs from '../../components/Jobs/Jobs';

function JobsPage() {
	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: true, link: '/jobs', name: 'Jobs'},
	];

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Jobs" />
				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />
				<ClayLayout.ContainerFluid className="jethr0-job-pages-menu">
					<ClayLayout.Row justify="between">
						<Heading level={3} weight="lighter">
							Jobs
						</Heading>
						<Jethr0ButtonsRow
							buttons={[{link: '/jobs/create', title: 'Create Job'}]}
						/>
					</ClayLayout.Row>
				</ClayLayout.ContainerFluid>
				<Jobs />
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default JobsPage;
