import Loading from 'shared/components/Loading';
import React from 'react';
import {getDefaultChannel} from 'shared/components/channels-menu';
import {Navigate, useParams} from 'react-router-dom';
import {Routes, toRoute} from 'shared/util/router';
import {useChannelContext} from 'shared/context/channel';
import {useDefaultChannelId} from 'shared/hooks/useDefaultChannelId';

const WorkspaceHomeRedirect: React.FC = () => {
	const {groupId = ''} = useParams<{groupId: string}>();

	const {channels} = useChannelContext();

	const {defaultChannelId, loading} = useDefaultChannelId({groupId});

	if (loading) {
		return <Loading />;
	}

	const channel = getDefaultChannel(defaultChannelId, channels);

	return (
		<Navigate
			replace
			to={toRoute(Routes.SITES, {
				...(channel && {channelId: channel.id}),
				groupId,
			})}
		/>
	);
};

export default WorkspaceHomeRedirect;
