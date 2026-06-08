/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';
import {useParams} from 'react-router-dom';
import Loading from '~/shared/components/Loading';
import BaseCard from '~/shared/components/base-card';
import {ChannelContext} from '~/shared/context/channel';
import {Interval, RangeSelectors} from '~/shared/types';

import ActivityStreamCard from './components/ActivityStreamCard';
import ActivityStreamCardHeader from './components/ActivityStreamCardHeader';

const Activities = () => {
	const {selectedChannel} = useContext(ChannelContext);

	const {channelId: routeChannelId, id} = useParams<{
		channelId: string;
		groupId: string;
		id: string;
	}>();

	const channelId = routeChannelId ?? selectedChannel?.id;

	if (!channelId) {
		return <Loading />;
	}

	return (
		<>
			<BaseCard
				Header={ActivityStreamCardHeader}
				className="account-activity-stream-card"
				description={Liferay.Language.get(
					'chronological-timeline-of-the-accounts-activities-within-the-selected-timeframe-with-details-on-events-and-session-context'
				)}
				headerProps={{showRangeKey: true}}
				label={Liferay.Language.get('activity-stream').toUpperCase()}
				legacyDropdownRangeKey={false}
				minHeight={500}
				showInterval
			>
				{({
					interval,
					rangeSelectors,
				}: {
					interval: Interval;
					rangeSelectors: RangeSelectors;
				}) => (
					<ActivityStreamCard
						accountId={id}
						channelId={channelId}
						interval={interval}
						rangeSelectors={rangeSelectors}
					/>
				)}
			</BaseCard>
		</>
	);
};

export default Activities;
