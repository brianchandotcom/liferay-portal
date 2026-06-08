/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import {PropTypes} from 'prop-types';
import React from 'react';
import {connect} from 'react-redux';
import AssociatedSegmentsCard from '~/contacts/components/AssociatedSegmentsCard';
import * as API from '~/shared/api';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import {EntityTypes, OrderByDirections} from '~/shared/util/constants';
import {INDIVIDUAL_COUNT, createOrderIOMap} from '~/shared/util/pagination';
import {Individual} from '~/shared/util/records';
import {INDIVIDUALS, Routes, toRoute} from '~/shared/util/router';
import URLConstants from '~/shared/util/url-constants';

import DetailsCard from '../components/DetailsCard';
import InterestsCard from '../components/InterestsCard';
import IndividualProfileCard from '../hoc/ProfileCard';

const ITEMS_PER_CARD = 5;

function fetchAssociatedSegments({channelId, groupId, id, searchValue}) {
	return API.individualSegment.search({
		channelId,
		contactsEntityId: id,
		contactsEntityType: EntityTypes.Individual,
		delta: ITEMS_PER_CARD,
		groupId,
		orderIOMap: createOrderIOMap(
			INDIVIDUAL_COUNT,
			OrderByDirections.Descending
		),
		query: searchValue,
	});
}

export class Overview extends React.Component {
	static propTypes = {
		channelId: PropTypes.string,
		groupId: PropTypes.string.isRequired,
		id: PropTypes.string.isRequired,
		individual: PropTypes.instanceOf(Individual).isRequired,
		tabId: PropTypes.string,
		timeZoneId: PropTypes.string,
	};

	render() {
		const {channelId, groupId, id, individual, tabId, timeZoneId} =
			this.props;

		return (
			<>
				<div className="overview-layout">
					<div className="overview-column-main">
						<IndividualProfileCard
							channelId={channelId}
							entity={individual}
							groupId={groupId}
							tabId={tabId}
							timeZoneId={timeZoneId}
						/>
					</div>

					<div className="overview-column-side">
						<DetailsCard
							channelId={channelId}
							entity={individual}
							groupId={groupId}
							timeZoneId={timeZoneId}
						/>

						<InterestsCard
							channelId={channelId}
							compact
							entity={individual}
							groupId={groupId}
							showFilter
							type={INDIVIDUALS}
						/>

						<AssociatedSegmentsCard
							channelId={channelId}
							dataSourceFn={fetchAssociatedSegments}
							groupId={groupId}
							id={id}
							noResultsRenderer={() => (
								<NoResultsDisplay
									description={
										<>
											{Liferay.Language.get(
												'create-a-segment-to-get-started'
											)}

											<ClayLink
												className="d-block"
												href={
													URLConstants.IndividualProfilesDocumentSegments
												}
												key="DOCUMENTATION"
												target="_blank"
											>
												{Liferay.Language.get(
													'learn-more-about-segments'
												)}
											</ClayLink>
										</>
									}
									spacer
									title={Liferay.Language.get(
										'there-are-no-segments-found'
									)}
								/>
							)}
							pageUrl={toRoute(
								Routes.CONTACTS_INDIVIDUAL_SEGMENTS,
								{
									channelId,
									groupId,
									id,
								}
							)}
						/>
					</div>
				</div>
			</>
		);
	}
}

export default connect((store, {groupId}) => ({
	timeZoneId: store.getIn([
		'projects',
		groupId,
		'data',
		'timeZone',
		'timeZoneId',
	]),
}))(Overview);
