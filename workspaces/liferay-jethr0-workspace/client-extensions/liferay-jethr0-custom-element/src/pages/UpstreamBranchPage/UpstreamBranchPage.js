/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import {useState} from 'react';
import {Link, useParams} from 'react-router-dom';

import './UpstreamBranchPage.css';
import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import {toLocaleString} from '../../services/DateUtil';
import useSpringBootData from '../../services/useSpringBootData';

const gitHubURLRegExp = new RegExp(
	'https://github.com/([^/]+)/([^/]+)/tree/([^/]+)'
);

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

	const gitHubURLMatch = upstreamBranch.branchURL.match(gitHubURLRegExp);

	const upstreamBranchName = gitHubURLMatch[3];
	const upstreamBranchRepositoryName = gitHubURLMatch[2];
	const upstreamBranchUserName = gitHubURLMatch[1];
	const upstreamBranchSHA = upstreamBranch.branchSHA;

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
				<Link to={upstreamBranch.branchURL}>{upstreamBranchName}</Link>
				<br />
				Branch SHA:{' '}
				<Link
					to={
						'https://github.com/' +
						upstreamBranchUserName +
						'/' +
						upstreamBranchRepositoryName +
						'/commit/' +
						upstreamBranchSHA
					}
				>
					{upstreamBranchSHA.substring(0, 7)}
				</Link>
				<br />
				Repository Name:{' '}
				<Link
					to={
						'https://github.com/' +
						upstreamBranchUserName +
						'/' +
						upstreamBranchRepositoryName
					}
				>
					{upstreamBranchRepositoryName}
				</Link>
				<br />
				User Name:{' '}
				<Link to={'https://github.com/' + upstreamBranchUserName}>
					{upstreamBranchUserName}
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
	const [upstreamBranch, setUpstreamBranch] = useState(null);

	useSpringBootData({
		setData: setUpstreamBranch,
		urlPath: '/git-branches/' + id,
	});

	let upstreamBranchTitle = 'Git Branch #' + id;

	if (upstreamBranch) {
		const gitHubURLMatch = upstreamBranch.branchURL.match(gitHubURLRegExp);

		const upstreamBranchName = gitHubURLMatch[3];
		const upstreamBranchRepositoryName = gitHubURLMatch[2];

		upstreamBranchTitle =
			upstreamBranchRepositoryName + '/' + upstreamBranchName;
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
				<ClayLayout.ContainerFluid className="jethr0-upstream-branch-page-menu">
					<ClayLayout.Row justify="between">
						<Heading level={3} weight="lighter">
							{upstreamBranchTitle}
						</Heading>
					</ClayLayout.Row>
				</ClayLayout.ContainerFluid>
				<UpstreamBranchInformation upstreamBranch={upstreamBranch} />
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default UpstreamBranchPage;
