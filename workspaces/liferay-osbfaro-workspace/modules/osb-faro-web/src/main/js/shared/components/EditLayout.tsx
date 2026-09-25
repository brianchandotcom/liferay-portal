import DataSourcesProvider from 'shared/context/dataSources';
import ErrorPage from 'shared/pages/ErrorPage';
import Loading from 'shared/components/Loading';
import MaintenanceAlert from 'shared/components/MaintenanceAlert';
import NoPropertiesAvailable from 'shared/pages/NoPropertiesAvailable';
import NotificationAlertList, {
	useNotificationsAPI,
} from 'shared/components/NotificationAlertList';
import ProjectStateDisplay from 'shared/components/workspaces/ProjectStateDisplay';
import React, {Suspense} from 'react';
import WorkspaceNotFound from 'shared/pages/WorkspaceNotFound';
import {Channel} from 'shared/components/channels-menu';
import {DownloadReportProvider} from 'shared/components/download-report/DownloadReportContext';
import {Outlet, useParams} from 'react-router-dom';
import {useChannels} from 'shared/hooks/useChannels';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useLDPEnabled} from 'shared/hooks/useLDPEnabled';
import {useProjectState} from 'shared/hooks/useProjectState';

export const isValidChannel = (
	channelId: string | undefined,
	channels: Channel[]
) =>
	!channelId || !channels.length || channels.some(({id}) => id === channelId);

const EditContent: React.FC<{groupId: string}> = ({groupId}) => {
	const {channelId} = useParams<{channelId?: string}>();

	const currentUser = useCurrentUser();

	const notificationResponse = useNotificationsAPI(groupId);

	const {channel, channels, error, loading} = useChannels({
		channelId,
		groupId,
	});

	if (error) {
		return <ErrorPage />;
	}

	if (loading) {
		return <Loading />;
	}

	if (!isValidChannel(channelId, channels)) {
		return <ErrorPage />;
	}

	if (!channel) {
		return (
			<NoPropertiesAvailable
				currentUser={currentUser}
				groupId={groupId}
			/>
		);
	}

	return (
		<DataSourcesProvider groupId={groupId}>
			<DownloadReportProvider>
				<MaintenanceAlert stripe />

				<NotificationAlertList
					{...notificationResponse}
					groupId={groupId}
					stripe
				/>

				<Suspense fallback={<Loading />}>
					<Outlet />
				</Suspense>
			</DownloadReportProvider>
		</DataSourcesProvider>
	);
};

const EditLayout: React.FC = () => {
	const {groupId = ''} = useParams<{groupId: string}>();

	const {error, loading, project} = useProjectState({groupId});

	if (error) {
		return <WorkspaceNotFound />;
	}

	if (loading || !project) {
		return <Loading />;
	}

	return (
		<ProjectStateDisplay project={project}>
			<EditContent groupId={groupId} />
		</ProjectStateDisplay>
	);
};

export const LDPEditLayout: React.FC = () => {
	const {groupId = ''} = useParams<{groupId: string}>();

	const LDPEnabled = useLDPEnabled({groupId});

	return LDPEnabled ? <Outlet /> : <ErrorPage />;
};

export default EditLayout;
