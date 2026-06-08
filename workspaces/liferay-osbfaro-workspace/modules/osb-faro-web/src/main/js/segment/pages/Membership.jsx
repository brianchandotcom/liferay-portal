/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PropTypes} from 'prop-types';
import React from 'react';
import {connect} from 'react-redux';
import SegmentGrowthWithList from '~/segment/components/Growth';
import * as API from '~/shared/api';
import Card from '~/shared/components/Card';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import {withRequest} from '~/shared/hoc';
import {mapGrowthHistory} from '~/shared/hoc/mappers/segment';
import {SegmentTypes} from '~/shared/util/constants';
import {Segment} from '~/shared/util/records';

export const MembershipChart = withRequest(
	API.individualSegment.fetchMembershipChangesAggregations,
	mapGrowthHistory,
	{
		alignCenter: true,
		page: false,
	}
)(
	class extends React.Component {
		static propTypes = {
			channelId: PropTypes.string,
			data: PropTypes.array,
			groupId: PropTypes.string.isRequired,
			id: PropTypes.string.isRequired,
			individualCounts: PropTypes.shape({
				anonymousCount: PropTypes.number,
				knownCount: PropTypes.number,
			}),
			segmentType: PropTypes.oneOf(Object.values(SegmentTypes)),
			timeZoneId: PropTypes.string,
		};

		render() {
			const {
				channelId,
				data,
				groupId,
				id,
				individualCounts,
				segmentType,
				timeZoneId,
			} = this.props;

			return (
				<SegmentGrowthWithList
					channelId={channelId}
					data={data}
					groupId={groupId}
					id={id}
					individualCounts={individualCounts}
					shouldShowMembershipList={
						segmentType !== SegmentTypes.RealTime
					}
					timeZoneId={timeZoneId}
				/>
			);
		}
	}
);

@connect((store, {groupId}) => ({
	timeZoneId: store.getIn([
		'projects',
		groupId,
		'data',
		'timeZone',
		'timeZoneId',
	]),
}))
export default class Membership extends React.Component {
	static propTypes = {
		groupId: PropTypes.string.isRequired,
		segment: PropTypes.instanceOf(Segment).isRequired,
		timeZoneId: PropTypes.string,
	};

	render() {
		const {
			channelId,
			groupId,
			segment: {
				anonymousIndividualCount,
				id,
				knownIndividualCount,
				segmentType,
			},
			timeZoneId,
		} = this.props;

		return (
			<Card
				className="segment-membership-root"
				reportContainer={ReportContainer.SegmentMembershipCard}
			>
				<Card.Header className="align-items-center d-flex justify-content-between">
					<Card.Title>
						{Liferay.Language.get('segment-membership-trend')}
					</Card.Title>
					<span className="text-secondary text-uppercase">
						<strong>{Liferay.Language.get('last-30-days')}</strong>
					</span>
				</Card.Header>

				<MembershipChart
					anonymousIndividualCount
					channelId={channelId}
					groupId={groupId}
					id={id}
					individualCounts={{
						anonymousCount: anonymousIndividualCount,
						knownCount: knownIndividualCount,
					}}
					segmentType={segmentType}
					timeZoneId={timeZoneId}
				/>
			</Card>
		);
	}
}
