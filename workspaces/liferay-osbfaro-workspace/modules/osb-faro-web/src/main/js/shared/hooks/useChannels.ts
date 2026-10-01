import * as API from 'shared/api';
import {ActionType, useChannelContext} from 'shared/context/channel';
import {Channel, getDefaultChannel} from 'shared/components/channels-menu';
import {useEffect, useMemo, useState} from 'react';
import {useRequest} from 'shared/hooks/useRequest';

export const useChannels = ({
	channelId,
	groupId,
}: {
	channelId?: string;
	groupId: string;
}) => {
	const {channelDispatch} = useChannelContext();

	const {data, error, loading, refetch} = useRequest<
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

	const [refetched, setRefetched] = useState(false);

	/**
	 * Only the first sync of the channel context holds the page back. Later
	 * channel switches update the context in place, so the page stays mounted.
	 */
	const [synced, setSynced] = useState(false);

	/**
	 * A channel missing from the list may have been created after the list
	 * loaded, as the onboarding does, so the list is reloaded once.
	 */
	const missingChannel =
		!loading &&
		!error &&
		!!channelId &&
		!!channels.length &&
		!channels.some(({id}) => id === channelId);

	useEffect(() => {
		if (missingChannel && !refetched) {
			setRefetched(true);

			refetch();
		}
	}, [missingChannel, refetch, refetched]);

	useEffect(() => {
		if (loading || error) {
			return;
		}

		channelDispatch?.({payload: channels, type: ActionType.setChannels});

		channelDispatch?.({
			payload: channel,
			type: ActionType.setSelectedChannel,
		});

		setSynced(true);
	}, [channel, channelDispatch, channels, error, loading]);

	return {
		channel,
		channels,
		error,
		loading: loading || (!error && !synced),
	};
};
