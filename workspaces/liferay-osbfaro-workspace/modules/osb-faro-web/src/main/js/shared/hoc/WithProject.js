import withAction from './WithAction';
import {
	fetchProject,
	fetchProjectViaCorpProjectUuid
} from '../actions/projects';

/**
 * HOC for wrapping a component with the current Project.
 * @param {boolean} bypassErrorPage - Determine whether to bypass the error page if an error occurs but data still exists.
 * @returns {Function} - The new component
 */
export const withProject = (bypassErrorPage = false) =>
	withAction(
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
							project =>
								String(project.getIn(['data', 'groupId'])) ===
								groupId
						) || state.getIn(['projects', groupId])
				);
			}

			return state.getIn(['projects', corpProjectUuid]);
		},
		{bypassErrorPage, propName: 'project'}
	);

export default withProject();
