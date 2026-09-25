import * as API from 'shared/api';
import * as data from 'test/data';
import React from 'react';
import {ChannelContext, ChannelProvider} from 'shared/context/channel';
import {renderHook, waitFor} from '@testing-library/react';
import {useChannels} from 'shared/hooks/useChannels';

jest.unmock('react-dom');

const CHANNELS = [
	data.mockChannel(1, 0, {id: '1'}),
	data.mockChannel(2, 0, {id: '2'}),
];

const wrapper = ({children}) => (
	<ChannelProvider>{children}</ChannelProvider>
);

describe('useChannels', () => {
	beforeEach(() => {
		API.channels.fetchAll.mockReturnValue(
			Promise.resolve({items: CHANNELS})
		);
	});

	it('selects the channel from the URL and fills the channel context', async () => {
		const {result} = renderHook(
			() => ({
				channels: useChannels({channelId: '2', groupId: '23'}),
				context: React.useContext(ChannelContext),
			}),
			{wrapper}
		);

		await waitFor(() => expect(result.current.channels.loading).toBe(false));

		expect(result.current.channels.channel).toEqual(CHANNELS[1]);
		expect(result.current.context.channels).toEqual(CHANNELS);
		expect(result.current.context.selectedChannel).toEqual(CHANNELS[1]);
	});

	it('falls back to the first channel when the URL has none', async () => {
		const {result} = renderHook(
			() => ({
				channels: useChannels({groupId: '23'}),
				context: React.useContext(ChannelContext),
			}),
			{wrapper}
		);

		await waitFor(() => expect(result.current.channels.loading).toBe(false));

		expect(result.current.context.selectedChannel).toEqual(CHANNELS[0]);
	});

	it('stays loading until the channel context matches the selected channel', async () => {
		const channelDispatch = jest.fn();

		const staleWrapper = ({children}) => (
			<ChannelContext.Provider
				value={{
					channelDispatch,
					channels: CHANNELS,
					selectedChannel: CHANNELS[0],
				}}
			>
				{children}
			</ChannelContext.Provider>
		);

		const {result} = renderHook(
			() => useChannels({channelId: '2', groupId: '23'}),
			{wrapper: staleWrapper}
		);

		await waitFor(() =>
			expect(channelDispatch).toHaveBeenCalledWith(
				expect.objectContaining({payload: CHANNELS[1]})
			)
		);

		expect(result.current.loading).toBe(true);
	});

	it('reports an error when the channels request fails', async () => {
		API.channels.fetchAll.mockReturnValue(
			Promise.reject(new Error('failed'))
		);

		const {result} = renderHook(
			() => useChannels({channelId: '1', groupId: '23'}),
			{wrapper}
		);

		await waitFor(() => expect(result.current.error).toBe(true));
	});
});
