/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import IndividualProfileCard from '~/individual/profile/components/ProfileCard';
import {RangeKeyTimeRanges} from '~/shared/util/constants';
import {Individual} from '~/shared/util/records';
import {mockIndividual} from '~/test/data';

class IndividualProfileCardKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<IndividualProfileCard
					channelId="123"
					delta={20}
					entity={new Individual(mockIndividual())}
					groupId="35205"
					interval="DAY"
					onChangeInterval={() => {}}
					onDeltaChange={() => {}}
					onPageChange={() => {}}
					onQueryChange={() => {}}
					onRangeSelectorsChange={() => {}}
					page={1}
					query=""
					rangeSelectors={{
						rangeKey: RangeKeyTimeRanges.Last30Days,
					}}
					resetPage={() => {}}
					tabId="activity"
				/>
			</div>
		);
	}
}

export default IndividualProfileCardKit;
