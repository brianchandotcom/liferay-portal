/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import {useState} from 'react';
import {Link} from 'react-router-dom';

import './UpstreamBranchesPage.css';
import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import Jethr0Table from '../../components/Jethr0Table/Jethr0Table';
import {toLocaleString} from '../../services/DateUtil';
import useSpringBootData from '../../services/useSpringBootData';

function UpstreamBranches() {
	const [gitBranches, setGitBranches] = useState(null);

	useSpringBootData({
		setData: setGitBranches,
		urlPath: '/git-branches/upstream',
	});

	if (!gitBranches) {
		return <div>Loading...</div>;
	}

	const gitHubURLRegExp = new RegExp(
		'https://github.com/([^/]+)/([^/]+)/tree/([^/]+)'
	);

	return (
		<Jethr0Table>
			<thead>
				<tr>
					<th>ID</th>
					<th>Branch Name</th>
					<th>Branch SHA</th>
					<th>Repository Name</th>
					<th>User Name</th>
					<th>Create Date</th>
					<th>Modified Date</th>
				</tr>
			</thead>
			<tbody>
				{gitBranches &&
					gitBranches.map((gitBranch) => {
						const gitHubURLMatch = gitBranch.branchURL.match(
							gitHubURLRegExp
						);

						const gitBranchName = gitHubURLMatch[3];
						const gitBranchRepositoryName = gitHubURLMatch[2];
						const gitBranchUserName = gitHubURLMatch[1];
						const gitBranchSHA = gitBranch.branchSHA;

						return (
							<tr key={gitBranch.id}>
								<th className="font-weight-semi-bold">
									<Link
										title={gitBranch.id}
										to={
											'/upstream-branches/' + gitBranch.id
										}
									>
										{gitBranch.id}
									</Link>
								</th>
								<td>
									<Link to={gitBranch.branchURL}>
										{gitBranchName}
									</Link>
								</td>
								<td>
									<Link
										to={
											'https://github.com/' +
											gitBranchUserName +
											'/' +
											gitBranchRepositoryName +
											'/commit/' +
											gitBranchSHA
										}
									>
										{gitBranchSHA.substring(0, 7)}
									</Link>
								</td>
								<td>
									<Link
										to={
											'https://github.com/' +
											gitBranchUserName +
											'/' +
											gitBranchRepositoryName
										}
									>
										{gitBranchRepositoryName}
									</Link>
								</td>
								<td>
									<Link
										to={
											'https://github.com/' +
											gitBranchUserName
										}
									>
										{gitBranchUserName}
									</Link>
								</td>
								<td>{toLocaleString(gitBranch.dateCreated)}</td>
								<td>
									{toLocaleString(gitBranch.dateModified)}
								</td>
							</tr>
						);
					})}
			</tbody>
		</Jethr0Table>
	);
}

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
