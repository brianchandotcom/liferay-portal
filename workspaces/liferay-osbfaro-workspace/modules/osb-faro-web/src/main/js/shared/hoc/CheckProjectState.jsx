import ProjectStateDisplay from 'shared/components/workspaces/ProjectStateDisplay';
import React from 'react';
import withAction from './WithAction';
import WorkspaceNotFound from 'shared/pages/WorkspaceNotFound';
import {compose} from 'redux';
import {fetchProject} from '../actions/projects';

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
			renderErrorPage: props => <WorkspaceNotFound {...props} />
		}
	),
	WrappedComponent =>
		({className, groupId, project, ...otherProps}) => (
			<ProjectStateDisplay className={className} project={project}>
				<WrappedComponent
					{...otherProps}
					className={className}
					groupId={groupId}
				/>
			</ProjectStateDisplay>
		)
);
