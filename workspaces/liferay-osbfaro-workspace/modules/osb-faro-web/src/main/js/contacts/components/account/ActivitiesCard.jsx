/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import autobind from 'autobind-decorator';
import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';
import ChangeLegend from '~/contacts/components/ChangeLegend';
import * as API from '~/shared/api';
import {DEFAULT_ACTIVITY_MAX} from '~/shared/api/activities';
import Card from '~/shared/components/Card';
import ErrorDisplay from '~/shared/components/ErrorDisplay';
import Loading from '~/shared/components/Loading';
import {buildLegendItems} from '~/shared/util/activitiesDeprecated';
import {getSafeChange} from '~/shared/util/change';
import {EntityTypes, TimeIntervals} from '~/shared/util/constants';
import {Account} from '~/shared/util/records';
import {autoCancel, hasRequest} from '~/shared/util/request-decorator';
import {Routes, toRoute} from '~/shared/util/router';

import ActivitiesChart from '../ActivitiesChartDeprecated';

@hasRequest
export default class ActivitiesCard extends React.Component {
	static propTypes = {
		account: PropTypes.instanceOf(Account).isRequired,
		channelId: PropTypes.string,
		groupId: PropTypes.string.isRequired,
	};

	state = {
		activityChange: 0,
		activityCount: 0,
		error: false,
		history: [],
		hoverIndex: -1,
		loading: true,
	};

	componentDidMount() {
		this.handleFetchHistory();
	}

	@autoCancel
	@autobind
	getActivityHistory() {
		const {
			account: {id},
			channelId,
			groupId,
		} = this.props;

		return API.activities.fetchHistory({
			channelId,
			contactsEntityId: id,
			contactsEntityType: EntityTypes.Account,
			groupId,
			interval: TimeIntervals.Day,
			max: DEFAULT_ACTIVITY_MAX,
		});
	}

	@autobind
	handleFetchHistory() {
		const {
			account: {id},
			channelId,
			groupId,
		} = this.props;

		this.setState({error: false, loading: true});

		API.activities
			.fetchHistory({
				channelId,
				contactsEntityId: id,
				contactsEntityType: EntityTypes.Account,
				groupId,
				interval: TimeIntervals.Day,
				max: DEFAULT_ACTIVITY_MAX,
			})
			.then(
				({
					activityAggregations: activityHistory,
					change: activityChange,
					count: activityCount,
				}) => {
					this.setState({
						activityChange: getSafeChange(activityChange),
						activityCount,
						history: activityHistory,
						loading: false,
					});
				}
			)
			.catch((error) => {
				if (!error.IS_CANCELLATION_ERROR) {
					this.setState({error: true, loading: false});
				}
			});
	}

	renderChart() {
		const {
			state: {activityChange, activityCount, error, history, loading},
		} = this;

		if (loading) {
			return <Loading key="LOADING" />;
		}
		else if (error) {
			return (
				<ErrorDisplay
					key="ERROR_DISPLAY"
					onReload={this.handleFetchHistory}
					spacer
				/>
			);
		}
		else {
			return (
				<>
					<ChangeLegend
						items={buildLegendItems({
							activityChange,
							activityCount,
						})}
					/>

					<ActivitiesChart
						history={history}
						interval={TimeIntervals.Day}
						rangeSelectors={{rangeKey: DEFAULT_ACTIVITY_MAX}}
					/>
				</>
			);
		}
	}

	render() {
		const {
			account: {id},
			channelId,
			className,
			groupId,
		} = this.props;

		return (
			<Card className={getCN('account-activities-card-root', className)}>
				<Card.Header>
					<Card.Title>
						{Liferay.Language.get('account-activities')}
					</Card.Title>
				</Card.Header>

				<Card.Body>{this.renderChart()}</Card.Body>

				<Card.Footer>
					<ClayLink
						borderless
						button
						className="button-root"
						displayType="secondary"
						href={toRoute(Routes.CONTACTS_ACCOUNT_ACTIVITIES, {
							channelId,
							groupId,
							id,
						})}
						small
					>
						{Liferay.Language.get('view-all-activities')}

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
