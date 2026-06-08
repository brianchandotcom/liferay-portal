/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {pickBy} from 'lodash';
import React from 'react';
import {useHistory} from 'react-router-dom';
import BaseCard from '~/shared/components/base-card';
import {useStatefulPagination} from '~/shared/hooks/useStatefulPagination';
import {Individual} from '~/shared/util/records';
import {removeUriQueryParam, setUriQueryValues} from '~/shared/util/router';

import ProfileCardWithData from '../components/ProfileCard';

const DEFAULT_SESSIONS_DELTA = 50;

interface IProfileCardProps extends React.HTMLAttributes<HTMLElement> {
	channelId: string;
	entity: Individual;
	groupId: string;
	tabId: string;
	timeZoneId: string;
}

const ProfileCard: React.FC<IProfileCardProps> = ({tabId, ...props}) => {
	const history = useHistory();
	const {
		delta,
		onDeltaChange,
		onPageChange,
		onQueryChange,
		page,
		query,
		resetPage,
	} = useStatefulPagination(undefined, {
		initialDelta: DEFAULT_SESSIONS_DELTA,
	});

	return (
		<>
			<BaseCard
				className="individual-profile-card-root page-display"
				headerProps={{
					showRangeKey: false,
					tabId,
				}}
				label={Liferay.Language.get('individual-events')}
				legacyDropdownRangeKey={false}
				showInterval={false}
			>
				{({
					interval,
					onChangeInterval,
					onRangeSelectorsChange,
					rangeSelectors,
				}) => (
					<ProfileCardWithData
						{...props}
						delta={delta}
						interval={interval}
						onChangeInterval={onChangeInterval}
						onDeltaChange={onDeltaChange}
						onPageChange={onPageChange}
						onQueryChange={(query) => {
							history.push(
								setUriQueryValues(
									pickBy({query}),
									removeUriQueryParam(
										window.location.href,
										'query'
									)
								)
							);

							onQueryChange(query);
						}}
						onRangeSelectorsChange={(rangeSelectors) => {
							history.push(
								setUriQueryValues(
									pickBy(rangeSelectors),
									removeUriQueryParam(
										window.location.href,
										'rangeEnd',
										'rangeStart'
									)
								)
							);

							onRangeSelectorsChange(rangeSelectors);
						}}
						page={page}
						query={query}
						rangeSelectors={rangeSelectors}
						resetPage={resetPage}
						tabId={tabId}
					/>
				)}
			</BaseCard>
		</>
	);
};

export default ProfileCard;
