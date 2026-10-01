import 'test/mock-modal';
import * as API from 'shared/api';
import mockStore from 'test/mock-store';
import React from 'react';
import {
	ActionType,
	UnassignedSegmentsContext,
} from 'shared/context/unassignedSegments';
import {ChannelContext} from 'shared/context/channel';
import {mockChannelContext} from 'test/mock-channel-context';
import {modalTypes, open} from 'shared/actions/modals';
import {Provider} from 'react-redux';
import {renderHook, waitFor} from '@testing-library/react';
import {useUnassignedSegmentsModal} from 'shared/hooks/useUnassignedSegmentsModal';

jest.unmock('react-dom');

const SEGMENTS = [{id: '1', name: 'Unassigned Segment'}];

const renderUseUnassignedSegmentsModal = (unassignedSegmentsDispatch) =>
	renderHook(() => useUnassignedSegmentsModal({groupId: '23'}), {
		wrapper: ({children}) => (
			<Provider store={mockStore()}>
				<ChannelContext.Provider value={mockChannelContext()}>
					<UnassignedSegmentsContext.Provider
						value={{
							unassignedSegments: [],
							unassignedSegmentsDispatch,
						}}
					>
						{children}
					</UnassignedSegmentsContext.Provider>
				</ChannelContext.Provider>
			</Provider>
		),
	});

describe('useUnassignedSegmentsModal', () => {
	beforeEach(() => {
		jest.clearAllMocks();

		API.individualSegment.searchUnassigned.mockReturnValue(
			Promise.resolve({items: SEGMENTS, total: SEGMENTS.length})
		);
	});

	it('shares the unassigned segments through their context', async () => {
		const unassignedSegmentsDispatch = jest.fn();

		renderUseUnassignedSegmentsModal(unassignedSegmentsDispatch);

		await waitFor(() =>
			expect(unassignedSegmentsDispatch).toHaveBeenCalledWith({
				payload: SEGMENTS,
				type: ActionType.setSegments,
			})
		);
	});

	it('opens the unassigned segments modal when the user has not seen it', async () => {
		API.preferences.fetchUpgradeModalSeen.mockReturnValueOnce(
			Promise.resolve(false)
		);

		renderUseUnassignedSegmentsModal(jest.fn());

		await waitFor(() =>
			expect(open).toHaveBeenCalledWith(
				modalTypes.UNASSIGNED_SEGMENTS_MODAL,
				expect.objectContaining({groupId: '23'}),
				{closeOnBlur: false}
			)
		);
	});

	it('does not open the unassigned segments modal when the user has seen it', async () => {
		API.preferences.fetchUpgradeModalSeen.mockReturnValueOnce(
			Promise.resolve(true)
		);

		const unassignedSegmentsDispatch = jest.fn();

		renderUseUnassignedSegmentsModal(unassignedSegmentsDispatch);

		await waitFor(() =>
			expect(unassignedSegmentsDispatch).toHaveBeenCalled()
		);

		expect(open).not.toHaveBeenCalled();
	});
});
