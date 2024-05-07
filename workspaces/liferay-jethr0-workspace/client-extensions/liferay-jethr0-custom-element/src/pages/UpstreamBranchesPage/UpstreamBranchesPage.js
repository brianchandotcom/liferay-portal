/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import {useState} from 'react';
import {Link} from 'react-router-dom';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0ContainerFluid from '../../components/Jethr0ContainerFluid/Jethr0ContainerFluid';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import Jethr0Table from '../../components/Jethr0Table/Jethr0Table';
import {getUpstreamGitBranches} from '../../objects/gitbranches/GitBranchUtil';
import {toLocaleString} from '../../services/DateUtil';

function UpstreamBranches() {
	const [upstreamGitBranches, setUpstreamGitBranches] = useState(null);

	if (!upstreamGitBranches) {
		getUpstreamGitBranches({setUpstreamGitBranches});
	}

	if (!upstreamGitBranches) {
		return <div>Loading...</div>;
	}

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
				{upstreamGitBranches?.map((upstreamGitBranch) => {
					return (
						<tr key={upstreamGitBranch.id}>
							<th className="font-weight-semi-bold">
								<Link
									title={upstreamGitBranch.id}
									to={
										'/upstream-branches/' +
										upstreamGitBranch.id
									}
								>
									{upstreamGitBranch.id}
								</Link>
							</th>
							<td>
								<Link to={upstreamGitBranch.url}>
									{upstreamGitBranch.name}
								</Link>
							</td>
							<td>
								{upstreamGitBranch.latestSHA && (
									<Link
										to={
											'https://github.com/' +
											upstreamGitBranch.userName +
											'/' +
											upstreamGitBranch.repositoryName +
											'/commit/' +
											upstreamGitBranch.latestSHA
										}
									>
										{upstreamGitBranch.latestSHA.substring(
											0,
											7
										)}
									</Link>
								)}
							</td>
							<td>
								<Link
									to={
										'https://github.com/' +
										upstreamGitBranch.userName +
										'/' +
										upstreamGitBranch.repositoryName
									}
								>
									{upstreamGitBranch.repositoryName}
								</Link>
							</td>
							<td>
								<Link
									to={
										'https://github.com/' +
										upstreamGitBranch.userName
									}
								>
									{upstreamGitBranch.userName}
								</Link>
							</td>
							<td>
								{toLocaleString(upstreamGitBranch.dateCreated)}
							</td>
							<td>
								{toLocaleString(upstreamGitBranch.dateModified)}
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

				<Jethr0ContainerFluid>
					<ClayLayout.Row justify="between">
						<Heading level={3} weight="lighter">
							Upstream Branches
						</Heading>
					</ClayLayout.Row>
				</Jethr0ContainerFluid>

				<UpstreamBranches />
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default UpstreamBranchesPage;
