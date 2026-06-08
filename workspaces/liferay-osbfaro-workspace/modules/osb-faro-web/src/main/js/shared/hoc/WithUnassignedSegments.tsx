/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect} from 'react';
import {ConnectedProps, connect} from 'react-redux';
import {close, modalTypes, open} from '~/shared/actions/modals';
import {
	fetchUpgradeModalSeen,
	updateUpgradeModalSeen,
} from '~/shared/actions/preferences';
import * as API from '~/shared/api';
import {useChannelContext} from '~/shared/context/channel';
import {
	ActionType,
	useUnassignedSegmentsContext,
} from '~/shared/context/unassignedSegments';
import {useRequest} from '~/shared/hooks/useRequest';
import {RootState} from '~/shared/store';

const connector = connect(
	(state: RootState) => ({
		upgradeModalSeen: state.getIn(
			['preferences', 'user', 'upgradeModalSeen', 'data'],
			true
		),
	}),
	{close, fetchUpgradeModalSeen, open, updateUpgradeModalSeen}
);

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IUnassignedSegmentsProps extends PropsFromRedux {
	groupId: string;
}

const withUnassignedSegments = (
	WrappedComponent: React.ComponentType<{
		groupId: any;
	}>
) => {
	const UnassignedSegments: React.FC<IUnassignedSegmentsProps> = ({
		close,
		fetchUpgradeModalSeen,
		groupId,
		open,
		updateUpgradeModalSeen,
		upgradeModalSeen,
		...otherProps
	}) => {
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
			fetchUpgradeModalSeen(groupId);

			// eslint-disable-next-line react-hooks/exhaustive-deps
		}, []);

		useEffect(() => {
			if (data && !error) {
				const {items, total} = data;

				unassignedSegmentsDispatch?.({
					payload: items,
					type: ActionType.setSegments,
				});

				if (
					!upgradeModalSeen &&
					!loading &&
					!!total &&
					!!channels.length
				) {
					open(
						modalTypes.UNASSIGNED_SEGMENTS_MODAL,
						{
							groupId,
							onClose: () => {
								updateUpgradeModalSeen({
									groupId,
									upgradeModalSeen: true,
								});

								close();
							},
						},
						{closeOnBlur: false}
					);
				}
			}

			// eslint-disable-next-line react-hooks/exhaustive-deps
		}, [data, error, loading, upgradeModalSeen]);

		return <WrappedComponent groupId={groupId} {...otherProps} />;
	};

	return connector(UnassignedSegments);
};

export default withUnassignedSegments;
