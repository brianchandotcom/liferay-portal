/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	fetchProject,
	fetchProjectViaCorpProjectUuid,
} from '../actions/projects';
import withAction from './WithAction';

/**
 * HOC for wrapping a component with the current Project.
 * @param {boolean} bypassErrorPage - Determine whether to bypass the error page if an error occurs but data still exists.
 * @returns {Function} - The new component
 */
export const withProject = function withProject(bypassErrorPage = false) {
	return withAction(
		({corpProjectUuid, groupId}) => {
			if (corpProjectUuid) {
				return fetchProjectViaCorpProjectUuid({corpProjectUuid});
			}

			return fetchProject({groupId});
		},
		(state, {corpProjectUuid, groupId}) => {
			if (groupId) {
				return (
					state
						.get('projects')
						.find(
							(project) =>
								String(project.getIn(['data', 'groupId'])) ===
								groupId
						) || state.getIn(['projects', groupId])
				);
			}

			return state.getIn(['projects', corpProjectUuid]);
		},
		{bypassErrorPage, propName: 'project'}
	);
};

export default withProject();
