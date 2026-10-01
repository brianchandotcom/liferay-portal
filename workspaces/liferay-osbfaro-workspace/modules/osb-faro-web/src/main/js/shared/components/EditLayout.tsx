import Loading from 'shared/components/Loading';
import MaintenanceAlert from 'shared/components/MaintenanceAlert';
import NoPropertiesAvailable from 'shared/pages/NoPropertiesAvailable';
import NotificationAlertList, {
	useNotificationsAPI,
} from 'shared/components/NotificationAlertList';
import React, {Suspense} from 'react';
import {Outlet, useParams} from 'react-router-dom';
import {useChannelContext} from 'shared/context/channel';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';

const EditLayout: React.FC = () => {
	const {groupId = ''} = useParams<{groupId: string}>();

	const {selectedChannel} = useChannelContext();

	const currentUser = useCurrentUser();

	const notificationResponse = useNotificationsAPI(groupId);

	if (!selectedChannel) {
		return (
			<NoPropertiesAvailable
				currentUser={currentUser}
				groupId={groupId}
			/>
		);
	}

	return (
		<>
			<MaintenanceAlert stripe />

			<NotificationAlertList
				{...notificationResponse}
				groupId={groupId}
				stripe
			/>

			<Suspense fallback={<Loading />}>
				<Outlet />
			</Suspense>
		</>
	);
};

export default EditLayout;
