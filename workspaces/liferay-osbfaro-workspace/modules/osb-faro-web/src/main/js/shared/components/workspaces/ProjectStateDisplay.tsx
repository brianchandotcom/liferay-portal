import ActivatingDisplay from 'shared/components/workspaces/ActivatingDisplay';
import React from 'react';
import SuccessDisplay from 'shared/components/workspaces/SuccessDisplay';
import WorkspacesErrorDisplay from 'shared/components/workspaces/ErrorDisplay';
import {ProjectStates} from 'shared/util/constants';

interface IProjectStateDisplayProps {
	children?: React.ReactNode;
	className?: string;
	project: {
		friendlyURL?: string;
		groupId: string;
		state: string;
	};
}

const ProjectStateDisplay: React.FC<IProjectStateDisplayProps> = ({
	children,
	className,
	project,
}) => {
	const {friendlyURL, groupId, state} = project;

	if (state === ProjectStates.Ready || state === ProjectStates.Scheduled) {
		return <>{children}</>;
	}

	if (
		state === ProjectStates.Deactivated ||
		state === ProjectStates.Maintenance ||
		state === ProjectStates.Unavailable
	) {
		return (
			<WorkspacesErrorDisplay className={className} errorType={state} />
		);
	}

	if (state === ProjectStates.Activating) {
		return <ActivatingDisplay groupId={groupId} />;
	}

	return <SuccessDisplay friendlyURL={friendlyURL || `/${groupId}`} />;
};

export default ProjectStateDisplay;
