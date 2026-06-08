/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '~/test/mock-modal';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {open} from '~/shared/actions/modals';
import * as API from '~/shared/api';
import {ChannelContext} from '~/shared/context/channel';
import {UnassignedSegmentsContext} from '~/shared/context/unassignedSegments';
import {mockSearch} from '~/test/data';
import {mockChannelContext} from '~/test/mock-channel-context';
import mockStore from '~/test/mock-store';

import withUnassignedSegments from '../WithUnassignedSegments';

jest.unmock('react-dom');

const WrappedComponent = withUnassignedSegments(() => 'wrapped component text');

const mockedContext = {
	unassignedSegments: [],
	unassignedSegmentsDispatch: jest.fn(),
};

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<ChannelContext.Provider value={mockChannelContext()}>
			<UnassignedSegmentsContext.Provider value={mockedContext}>
				<WrappedComponent {...props} />
			</UnassignedSegmentsContext.Provider>
		</ChannelContext.Provider>
	</Provider>
);

describe('WithUnassignedSegments', () => {
	afterEach(() => {
		cleanup();
		jest.clearAllMocks();
	});

	it('renders the wrapped component', () => {
		API.individualSegment.search.mockReturnValueOnce(
			Promise.resolve(mockSearch(() => {}, 0))
		);

		const {container} = render(<DefaultComponent />);

		expect(container.textContent).toBe('wrapped component text');
	});

	it.skip('triggers the unassigned segments modal if there are segments', () => {
		render(<DefaultComponent />);

		jest.runAllTimers();

		expect(open).toBeCalled();
	});

	it('does not trigger the unassigned segments modal if it has already been triggered', () => {
		API.preferences.fetchUpgradeModalSeen.mockReturnValueOnce(
			Promise.resolve(true)
		);

		render(
			<Provider store={mockStore()}>
				<ChannelContext.Provider value={mockChannelContext()}>
					<UnassignedSegmentsContext.Provider
						value={{
							...mockedContext,
						}}
					>
						<WrappedComponent />
					</UnassignedSegmentsContext.Provider>
				</ChannelContext.Provider>
			</Provider>
		);

		jest.runAllTimers();

		expect(open).not.toBeCalled();
	});
});
