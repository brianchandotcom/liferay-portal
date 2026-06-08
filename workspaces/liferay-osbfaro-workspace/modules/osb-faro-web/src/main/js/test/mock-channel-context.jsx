/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ChannelContext} from '~/shared/context/channel';

import {mockChannel} from './data';

export const mockChannelContext = function mockChannelContext() {
	return {
		channelDispatch: jest.fn(() => null),
		channels: [mockChannel(1), mockChannel(2)],
		selectedChannel: mockChannel(),
	};
};

export function withChannelProvider(Component) {
	return (props) => (
		<ChannelContext.Provider value={mockChannelContext()}>
			<Component {...props} />
		</ChannelContext.Provider>
	);
}
