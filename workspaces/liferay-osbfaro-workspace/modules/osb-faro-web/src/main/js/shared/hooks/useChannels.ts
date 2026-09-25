import * as API from 'shared/api';
import {ActionType, useChannelContext} from 'shared/context/channel';
import {Channel, getDefaultChannel} from 'shared/components/channels-menu';
import {useEffect, useMemo} from 'react';
import {useRequest} from 'shared/hooks/useRequest';

export const useChannels = ({
	channelId,
	groupId,
}: {
	channelId?: string;
	groupId: string;
}) => {
	const {channelDispatch, selectedChannel} = useChannelContext();

	const {data, error, loading} = useRequest<
		{groupId: string},
		{items: Channel[]}
	>({
		dataSourceFn: API.channels.fetchAll,
		variables: {groupId},
	});

	const channels = useMemo(() => data?.items ?? [], [data]);

	const channel = useMemo(
		() => getDefaultChannel(channelId, channels),
		[channelId, channels]
	);

	useEffect(() => {
		if (loading || error) {
			return;
		}

		channelDispatch?.({payload: channels, type: ActionType.setChannels});

		channelDispatch?.({
			payload: channel,
			type: ActionType.setSelectedChannel,
		});
	}, [channel, channelDispatch, channels, error, loading]);

	return {
		channel,
		channels,
		error,
		loading:
			loading ||
			(!error && !!channel && selectedChannel?.id !== channel.id),
	};
};
