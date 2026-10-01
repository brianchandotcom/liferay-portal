import * as API from 'shared/api';
import {
	ActionType,
	useUnassignedSegmentsContext,
} from 'shared/context/unassignedSegments';
import {close, modalTypes, open} from 'shared/actions/modals';
import {
	fetchUpgradeModalSeen,
	updateUpgradeModalSeen,
} from 'shared/actions/preferences';
import {RootState} from 'shared/store';
import {useChannelContext} from 'shared/context/channel';
import {useDispatch, useSelector} from 'react-redux';
import {useEffect} from 'react';
import {useRequest} from 'shared/hooks/useRequest';

export const useUnassignedSegmentsModal = ({groupId}: {groupId: string}) => {
	const dispatch = useDispatch();

	const upgradeModalSeen = useSelector((state: RootState) =>
		state.getIn(['preferences', 'user', 'upgradeModalSeen', 'data'], true)
	);

	const {unassignedSegmentsDispatch} = useUnassignedSegmentsContext();

	const {channels} = useChannelContext();

	const {data, error, loading} = useRequest({
		dataSourceFn: API.individualSegment.searchUnassigned,
		variables: {
			delta: 10000,
			groupId,
		},
	});

	useEffect(() => {
		dispatch(fetchUpgradeModalSeen(groupId) as any);
	}, [dispatch, groupId]);

	useEffect(() => {
		if (!data || error) {
			return;
		}

		const {items, total} = data;

		unassignedSegmentsDispatch?.({
			payload: items,
			type: ActionType.setSegments,
		});

		if (!upgradeModalSeen && !loading && !!total && !!channels.length) {
			dispatch(
				open(
					modalTypes.UNASSIGNED_SEGMENTS_MODAL,
					{
						groupId,
						onClose: () => {
							dispatch(
								updateUpgradeModalSeen({
									groupId,
									upgradeModalSeen: true,
								}) as any
							);

							dispatch(close());
						},
					},
					{closeOnBlur: false}
				)
			);
		}
	}, [data, error, loading, upgradeModalSeen]);
};
