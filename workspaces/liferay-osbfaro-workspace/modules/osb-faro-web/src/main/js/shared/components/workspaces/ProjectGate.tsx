import Loading from 'shared/components/Loading';
import ProjectStateDisplay from 'shared/components/workspaces/ProjectStateDisplay';
import React from 'react';
import WorkspaceNotFound from 'shared/pages/WorkspaceNotFound';
import {useProjectState} from 'shared/hooks/useProjectState';

interface IProjectGateProps {
	children: React.ReactNode;
	groupId: string;
}

const ProjectGate: React.FC<IProjectGateProps> = ({children, groupId}) => {
	const {error, loading, project} = useProjectState({groupId});

	if (error) {
		return <WorkspaceNotFound />;
	}

	if (loading || !project) {
		return <Loading />;
	}

	return (
		<ProjectStateDisplay project={project}>{children}</ProjectStateDisplay>
	);
};

export default ProjectGate;
