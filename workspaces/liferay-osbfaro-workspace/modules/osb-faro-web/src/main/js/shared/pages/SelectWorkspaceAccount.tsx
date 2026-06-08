/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isString} from 'lodash';
import React from 'react';
import {Redirect} from 'react-router';
import Loading from '~/shared/components/Loading';
import WorkspacesBasePage from '~/shared/components/workspaces/BasePage';
import WorkspaceList from '~/shared/components/workspaces/workspace-list';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {useFetchProjects} from '~/shared/hooks/useProjects';
import {sub} from '~/shared/util/lang';
import {getBasicProjects, getSingleProjectRoute} from '~/shared/util/projects';
import {Routes, setUriQueryValue, toRoute} from '~/shared/util/router';

const checkDisabled = ({configured}: {configured: boolean}) => configured;

export const routingFn = function routingFn({projects}: {projects: any[]}) {
	const basicProjects = getBasicProjects(projects);

	if (basicProjects.length === 1) {
		return getSingleProjectRoute(basicProjects[0]);
	}

	if (!basicProjects.some((basicProject) => !basicProject.get('groupId'))) {
		return setUriQueryValue(
			toRoute(Routes.WORKSPACES),
			'allBasicConfigured',
			true
		);
	}

	return null;
};

const SelectWorkspaceAccount = () => {
	const currentUser = useCurrentUser();
	const {data: projects, loading} = useFetchProjects();

	if (loading) {
		return <Loading />;
	}

	const route = routingFn({projects});

	if (isString(route)) {
		return <Redirect push to={route} />;
	}

	return (
		<div className="select-account-root">
			<WorkspacesBasePage
				details={[
					<p key="SELECT">
						{sub(
							Liferay.Language.get(
								'weve-found-multiple-accounts-associated-with-x-.-you-can-have-one-basic-tier-workspace-of-analytics-cloud-per-account.-please-associate-this-analytics-cloud-workspace-to-an-account'
							),
							[
								<b key="emailAddress">
									{currentUser.emailAddress}
								</b>,
							],
							false
						)}
					</p>,
				]}
				title={Liferay.Language.get('select-account')}
			>
				<WorkspaceList
					accounts={getBasicProjects(projects)}
					checkDisabled={checkDisabled}
				/>
			</WorkspacesBasePage>
		</div>
	);
};

export default SelectWorkspaceAccount;
