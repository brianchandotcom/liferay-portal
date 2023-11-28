/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';

import './UpstreamBranchesPage.css';
import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import UpstreamBranches from '../../components/UpstreamBranches/UpstreamBranches';

function UpstreamBranchesPage() {
	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: true, link: '/upstream-branches', name: 'Upstream Branches'},
	];

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Upstream Branches" />
				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />
				<ClayLayout.ContainerFluid className="jethr0-upstream-branch-pages-menu">
					<ClayLayout.Row justify="between">
						<Heading level={3} weight="lighter">
							Upstream Branches
						</Heading>
					</ClayLayout.Row>
				</ClayLayout.ContainerFluid>
				<UpstreamBranches />
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default UpstreamBranchesPage;
