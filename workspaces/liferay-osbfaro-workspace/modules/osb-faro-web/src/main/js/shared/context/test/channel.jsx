/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {mockChannel} from '~/test/data';
import {mockChannelContext} from '~/test/mock-channel-context';

import {
	ActionType,
	ChannelContext,
	ChannelProvider,
	channelReducer,
	useChannelContext,
} from '../channel';

jest.unmock('react-dom');

const initialState = {
	channels: [],
	selectedChannel: null,
};

describe('channelsReducer', () => {
	it('returns selectedChannel state with added item', () => {
		const channel = mockChannel();

		const {selectedChannel} = channelReducer(initialState, {
			payload: channel,
			type: ActionType.setSelectedChannel,
		});

		expect(selectedChannel).toBe(channel);
	});

	it('returns channels state with added item', () => {
		const channel = mockChannel();

		const {channels} = channelReducer(initialState, {
			payload: [channel],
			type: ActionType.setChannels,
		});

		expect(channels).toContain(channel);
	});
});

describe('ChannelProvider', () => {
	afterEach(cleanup);

	it('allows an initial context value to be set through the channel prop', () => {
		const successMsg = 'has channel intialized in context!';

		const ChildComponent = () => {
			const {selectedChannel} = useChannelContext();

			return selectedChannel && successMsg;
		};

		const channel = mockChannel(1);

		const {container} = render(
			<ChannelProvider selectedChannel={channel}>
				<ChildComponent />
			</ChannelProvider>
		);

		expect(container).toHaveTextContent(successMsg);
	});
});

describe('useChannelContext', () => {
	afterEach(cleanup);

	it('returns context', () => {
		const successMsg = 'has channel context!';
		const ChildComponent = () => {
			const {channelDispatch, channels, selectedChannel} =
				useChannelContext();

			return channels && selectedChannel && channelDispatch && successMsg;
		};

		const {container} = render(
			<ChannelContext.Provider value={mockChannelContext()}>
				<ChildComponent />
			</ChannelContext.Provider>
		);

		expect(container).toHaveTextContent(successMsg);
	});
});
