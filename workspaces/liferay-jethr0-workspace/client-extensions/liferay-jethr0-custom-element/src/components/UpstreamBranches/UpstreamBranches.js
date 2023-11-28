/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';
import {Link} from 'react-router-dom';

import {toLocaleString} from '../../services/DateUtil';
import useSpringBootData from '../../services/useSpringBootData';
import Jethr0Table from '../Jethr0Table/Jethr0Table';

function UpstreamBranches() {
	const [gitBranches, setGitBranches] = useState(null);

	useSpringBootData({
		setData: setGitBranches,
		urlPath: '/git-branches/upstream',
	});

	if (!gitBranches) {
		return <div>Loading...</div>;
	}

	const gitHubURLRegExp = new RegExp("https://github.com/([^/]+)/([^/]+)/tree/([^/]+)");

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
						const gitHubURLMatch = gitBranch.branchURL.match(gitHubURLRegExp);

						const gitBranchName = gitHubURLMatch[3];
						const gitBranchRepositoryName = gitHubURLMatch[2];
						const gitBranchUserName = gitHubURLMatch[1];

						return (
							<tr key={gitBranch.id}>
								<th className="font-weight-semi-bold">
									<Link title={gitBranch.id} to={'/jobs/' + gitBranch.id}>
										{gitBranch.id}
									</Link>
								</th>
								<td>
									<Link to={gitBranch.branchURL}>
										{gitBranchName}
									</Link>
								</td>
								<td>
									<Link to={'https://github.com/' + gitBranchUserName + '/' + gitBranchRepositoryName + '/commit/' + gitBranch.branchSHA}>
										{gitBranch.branchSHA.substring(0, 7)}
									</Link>
								</td>
								<td>
									<Link to={'https://github.com/' + gitBranchUserName + '/' + gitBranchRepositoryName}>
										{gitBranchRepositoryName}
									</Link>
								</td>
								<td>
									<Link to={'https://github.com/' + gitBranchUserName}>
										{gitBranchUserName}
									</Link>
								</td>
								<td>{toLocaleString(gitBranch.dateCreated)}</td>
								<td>{toLocaleString(gitBranch.dateModified)}</td>
							</tr>
						);
					})}
			</tbody>
		</Jethr0Table>
	);
}

export default UpstreamBranches;
