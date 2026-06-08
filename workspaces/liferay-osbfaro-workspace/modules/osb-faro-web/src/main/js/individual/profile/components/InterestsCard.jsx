/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';
import * as API from '~/shared/api';
import Card from '~/shared/components/Card';
import ListGroup from '~/shared/components/list-group';
import {compose, withEmpty, withRequest} from '~/shared/hoc';
import {NAME, createOrderIOMap} from '~/shared/util/pagination';
import {Routes, toRoute} from '~/shared/util/router';
import URLConstants from '~/shared/util/url-constants';

const ITEMS_PER_CARD = 6;

function fetchInterestData({channelId, groupId, id}) {
	return API.interests.search({
		channelId,
		contactsEntityId: id,
		delta: ITEMS_PER_CARD,
		groupId,
		orderIOMap: createOrderIOMap(NAME),
		page: 1,
	});
}

export const InterestsList = function InterestsList({
	channelId,
	groupId,
	id,
	interests,
}) {
	return (
		<ListGroup className="results-container" noBorder>
			{interests.map(({name}) => (
				<ListGroup.Item className="interest" key={name}>
					<ListGroup.ItemTitle className="text-truncate">
						{name ? (
							<ClayLink
								decoration="none"
								href={toRoute(
									Routes.CONTACTS_INDIVIDUAL_INTEREST_DETAILS,
									{
										channelId,
										groupId,
										id,
										interestId: name,
									}
								)}
							>
								{name}
							</ClayLink>
						) : (
							name
						)}
					</ListGroup.ItemTitle>
				</ListGroup.Item>
			))}
		</ListGroup>
	);
};

const ListWithInterests = compose(
	withRequest(
		fetchInterestData,
		(data) => ({
			channelId: data.channelId,
			interests: data.items,
			total: data.total,
		}),
		{
			page: false,
		}
	),
	withEmpty({
		description: (
			<>
				<span className="mr-1">
					{Liferay.Language.get(
						'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
					)}
				</span>

				<ClayLink
					href={URLConstants.IndividualProfilesDocumentInterests}
					key="DOCUMENTATION"
					target="_blank"
				>
					{Liferay.Language.get('learn-more-about-interests')}
				</ClayLink>
			</>
		),
		spacer: true,
		title: Liferay.Language.get('there-are-no-interests-found'),
	})
)(InterestsList);

export default class InterestsCard extends React.PureComponent {
	static propTypes = {
		channelId: PropTypes.string,
		entity: PropTypes.object.isRequired,
		groupId: PropTypes.string.isRequired,
	};

	render() {
		const {
			props: {
				channelId,
				className,
				entity: {id},
				groupId,
			},
		} = this;

		const classes = getCN('individual-interests-card-root', className);

		return (
			<Card className={classes}>
				<Card.Header>
					<Card.Title>
						{Liferay.Language.get('current-interests')}
					</Card.Title>
				</Card.Header>

				<ListWithInterests
					channelId={channelId}
					groupId={groupId}
					id={id}
				/>

				<Card.Footer>
					<ClayLink
						borderless
						button
						className="button-root"
						displayType="secondary"
						href={toRoute(Routes.CONTACTS_INDIVIDUAL_INTERESTS, {
							channelId,
							groupId,
							id,
						})}
						small
					>
						{Liferay.Language.get('view-all-interests')}

						<ClayIcon
							className="icon-root ml-2"
							symbol="angle-right-small"
						/>
					</ClayLink>
				</Card.Footer>
			</Card>
		);
	}
}
