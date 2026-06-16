import * as API from 'shared/api';
import ActivitiesChart from '../ActivitiesChartDeprecated';
import autobind from 'autobind-decorator';
import Card from 'shared/components/Card';
import ChangeLegend from 'contacts/components/ChangeLegend';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ErrorDisplay from 'shared/components/ErrorDisplay';
import getCN from 'classnames';
import Loading from 'shared/components/Loading';
import React from 'react';
import {Account} from 'shared/util/records';
import {autoCancel, hasRequest} from 'shared/util/request-decorator';
import {buildLegendItems} from 'shared/util/activitiesDeprecated';
import {DEFAULT_ACTIVITY_MAX} from 'shared/api/activities';
import {EntityTypes, TimeIntervals} from 'shared/util/constants';
import {getSafeChange} from 'shared/util/change';
import {PropTypes} from 'prop-types';
import {Routes, toRoute} from 'shared/util/router';

@hasRequest
export default class ActivitiesCard extends React.Component {
	static propTypes = {
		account: PropTypes.instanceOf(Account).isRequired,
		channelId: PropTypes.string,
		groupId: PropTypes.string.isRequired
	};

	state = {
		activityChange: 0,
		activityCount: 0,
		error: false,
		history: [],
		hoverIndex: -1,
		loading: true
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
			groupId
		} = this.props;

		return API.activities.fetchHistory({
			channelId,
			contactsEntityId: id,
			contactsEntityType: EntityTypes.Account,
			groupId,
			interval: TimeIntervals.Day,
			max: DEFAULT_ACTIVITY_MAX
		});
	}

	@autobind
	handleFetchHistory() {
		const {
			account: {id},
			channelId,
			groupId
		} = this.props;

		this.setState({error: false, loading: true});

		API.activities
			.fetchHistory({
				channelId,
				contactsEntityId: id,
				contactsEntityType: EntityTypes.Account,
				groupId,
				interval: TimeIntervals.Day,
				max: DEFAULT_ACTIVITY_MAX
			})
			.then(
				({
					activityAggregations: activityHistory,
					change: activityChange,
					count: activityCount
				}) => {
					this.setState({
						activityChange: getSafeChange(activityChange),
						activityCount,
						history: activityHistory,
						loading: false
					});
				}
			)
			.catch(err => {
				if (!err.IS_CANCELLATION_ERROR) {
					this.setState({error: true, loading: false});
				}
			});
	}

	renderChart() {
		const {
			state: {activityChange, activityCount, error, history, loading}
		} = this;

		if (loading) {
			return <Loading key='LOADING' />;
		} else if (error) {
			return (
				<ErrorDisplay
					key='ERROR_DISPLAY'
					onReload={this.handleFetchHistory}
					spacer
				/>
			);
		} else {
			return (
				<>
					<ChangeLegend
						items={buildLegendItems({
							activityChange,
							activityCount
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
			groupId
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
						className='button-root'
						displayType='secondary'
						href={toRoute(Routes.CONTACTS_ACCOUNT_ACTIVITIES, {
							channelId,
							groupId,
							id
						})}
						small
					>
						{Liferay.Language.get('view-all-activities')}

						<ClayIcon
							className='icon-root ml-2'
							symbol='angle-right-small'
						/>
					</ClayLink>
				</Card.Footer>
			</Card>
		);
	}
}
