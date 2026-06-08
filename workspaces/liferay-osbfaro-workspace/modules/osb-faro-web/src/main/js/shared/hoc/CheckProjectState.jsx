/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {compose} from 'redux';
import ActivatingDisplay from '~/shared/components/workspaces/ActivatingDisplay';
import WorkspacesErrorDisplay from '~/shared/components/workspaces/ErrorDisplay';
import SuccessDisplay from '~/shared/components/workspaces/SuccessDisplay';
import WorkspaceNotFound from '~/shared/pages/WorkspaceNotFound';
import {ProjectStates} from '~/shared/util/constants';

import {fetchProject} from '../actions/projects';
import withAction from './WithAction';

/**
 * HOC for conditionally rendering SettingUpWorkspace.
 * If the project state is not ready, we will render SettingUpWorkspace.
 * @returns {Function} - The new component
 */
export default compose(
	withAction(
		({groupId}) => fetchProject({groupId}),
		(state, {groupId}) => state.getIn(['projects', groupId]),
		{
			propName: 'project',
			renderErrorPage: (props) => <WorkspaceNotFound {...props} />,
		}
	),
	(WrappedComponent) =>
		({className, groupId, project, ...otherProps}) => {
			switch (project.state) {
				case ProjectStates.Ready:
				case ProjectStates.Scheduled:
					return (
						<WrappedComponent
							{...otherProps}
							className={className}
							groupId={groupId}
						/>
					);

				case ProjectStates.Deactivated:
				case ProjectStates.Maintenance:
				case ProjectStates.Unavailable:
					return (
						<WorkspacesErrorDisplay
							className={className}
							errorType={project.state}
						/>
					);

				case ProjectStates.Activating:
					return <ActivatingDisplay groupId={project.groupId} />;

				default:
					return (
						<SuccessDisplay
							friendlyURL={
								project.friendlyURL || `/${project.groupId}`
							}
						/>
					);
			}
		}
);
