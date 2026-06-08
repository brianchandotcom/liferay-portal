/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useReducer} from 'react';
import {Channel} from '~/shared/components/channels-menu';

export enum ActionType {
	setChannels = 'setChannels',
	setSelectedChannel = 'setSelectedChannel',
}

type Action = {
	payload: any;
	type: ActionType;
};

type State = {
	channels: Array<Channel>;
	selectedChannel: Channel | null;
};

type ChannelProviderProps = {
	children: React.ReactNode;
	selectedChannel?: Channel | null;
};

export const ChannelContext = React.createContext<{
	channelDispatch?: React.Dispatch<Action>;
	channels: Array<Channel>;
	selectedChannel: Channel | null;
}>({
	channels: [],
	selectedChannel: null,
});

export const channelReducer = function channelReducer(
	state: State,
	{payload, type}: Action
) {
	switch (type) {
		case ActionType.setChannels: {
			return {
				...state,
				channels: payload,
			};
		}
		case ActionType.setSelectedChannel: {
			return {
				...state,
				selectedChannel: payload,
			};
		}
		default:
			return state;
	}
};

export const ChannelProvider = function ChannelProvider({
	children,
	selectedChannel: channelProp = null,
}: ChannelProviderProps) {
	const [{channels, selectedChannel}, channelDispatch] = useReducer(
		channelReducer,
		{
			channels: [],
			selectedChannel: channelProp || null,
		}
	);

	return (
		<ChannelContext.Provider
			value={{channelDispatch, channels, selectedChannel}}
		>
			{children}
		</ChannelContext.Provider>
	);
};

export default ChannelProvider;

export const useChannelContext = function useChannelContext() {
	return useContext(ChannelContext);
};
