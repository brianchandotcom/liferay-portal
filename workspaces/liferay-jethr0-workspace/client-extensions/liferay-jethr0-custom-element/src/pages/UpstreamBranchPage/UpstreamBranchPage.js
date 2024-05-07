/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import {useState} from 'react';
import {Link, useParams} from 'react-router-dom';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0ContainerFluid from '../../components/Jethr0ContainerFluid/Jethr0ContainerFluid';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import {getUpstreamGitBranch} from '../../objects/gitbranches/GitBranchUtil';
import {toLocaleString} from '../../services/DateUtil';

function UpstreamBranchInformation({upstreamBranch}) {
	if (!upstreamBranch) {
		return (
			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle="Upstream Branch Information"
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
			displayTitle="Upstream Branch Information"
			displayType="secondary"
		>
			<ClayPanel.Body>
				Branch ID: {upstreamBranch.id}
				<br />
				Branch Name:{' '}
				<Link to={upstreamBranch.url}>{upstreamBranch.name}</Link>
				<br />
				Branch SHA:{' '}
				{upstreamBranch.latestSHA && (
					<Link
						to={
							'https://github.com/' +
							upstreamBranch.userName +
							'/' +
							upstreamBranch.repositoryName +
							'/commit/' +
							upstreamBranch.latestSHA
						}
					>
						{upstreamBranch.latestSHA.substring(0, 7)}
					</Link>
				)}
				<br />
				Repository Name:{' '}
				<Link
					to={
						'https://github.com/' +
						upstreamBranch.userName +
						'/' +
						upstreamBranch.repositoryName
					}
				>
					{upstreamBranch.repositoryName}
				</Link>
				<br />
				User Name:{' '}
				<Link to={'https://github.com/' + upstreamBranch.userName}>
					{upstreamBranch.userName}
				</Link>
				<br />
				Create Date:
				{' ' + toLocaleString(upstreamBranch.dateCreated)}
				<br />
				Modified Date:
				{' ' + toLocaleString(upstreamBranch.dateModified)}
			</ClayPanel.Body>
		</ClayPanel>
	);
}

function UpstreamBranchPage() {
	const {id} = useParams();
	const [upstreamGitBranch, setUpstreamGitBranch] = useState(null);

	if (!upstreamGitBranch) {
		getUpstreamGitBranch({id, setUpstreamGitBranch});
	}

	let upstreamBranchTitle = 'Git Branch #' + id;

	if (upstreamGitBranch) {
		upstreamBranchTitle =
			upstreamGitBranch.userName +
			'/' +
			upstreamGitBranch.repositoryName +
			'/' +
			upstreamGitBranch.name;
	}

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/upstream-branches', name: 'Upstream Branches'},
		{
			active: true,
			link: '/upstream-branches/' + id,
			name: upstreamBranchTitle,
		},
	];

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Upstream Branches" />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Jethr0ContainerFluid>
					<ClayLayout.Row justify="between">
						<Heading level={3} weight="lighter">
							{upstreamBranchTitle}
						</Heading>
					</ClayLayout.Row>
				</Jethr0ContainerFluid>

				<UpstreamBranchInformation upstreamBranch={upstreamGitBranch} />
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default UpstreamBranchPage;
