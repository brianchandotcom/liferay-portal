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
import Card from '~/shared/components/Card';
import ErrorDisplay from '~/shared/components/ErrorDisplay';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import Table from '~/shared/components/table';
import {autoCancel, hasRequest} from '~/shared/util/request-decorator';
import {Routes, toRoute} from '~/shared/util/router';
import {individualsListColumns} from '~/shared/util/table-columns';
import URLConstants from '~/shared/util/url-constants';

@hasRequest
export default class KnownIndividualsCard extends React.Component {
	static propTypes = {
		channelId: PropTypes.string,
		dataSourceFn: PropTypes.func.isRequired,
		groupId: PropTypes.string.isRequired,
		id: PropTypes.string.isRequired,
	};

	state = {
		error: false,
		items: [],
		loading: true,
	};

	componentDidMount() {
		this.handleFetchItems();
	}

	@autoCancel
	@autobind
	handleFetchItems() {
		const {channelId, dataSourceFn, groupId, id} = this.props;

		this.setState({
			error: false,
			loading: true,
		});

		return dataSourceFn({channelId, groupId, id})
			.then(({items}) => {
				this.setState({
					items,
					loading: false,
				});
			})
			.catch((error) => {
				if (!error.IS_CANCELLATION_ERROR) {
					this.setState({
						error: true,
						loading: false,
					});
				}
			});
	}

	renderTable() {
		const {
			props: {channelId, groupId},
			state: {error, items, loading},
		} = this;

		if (error) {
			return (
				<ErrorDisplay
					key="ERROR_DISPLAY"
					onReload={this.handleFetchItems}
					spacer
				/>
			);
		}
		else if (!loading && !items.length) {
			return (
				<NoResultsDisplay
					description={
						<>
							<span className="mr-1">
								{Liferay.Language.get(
									'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
								)}
							</span>

							<ClayLink
								href={
									URLConstants.IndividualsDashboardDocumentation
								}
								key="DOCUMENTATION"
								target="_blank"
							>
								{Liferay.Language.get(
									'learn-more-about-individuals'
								)}
							</ClayLink>
						</>
					}
					key="NO_RESULTS_DISPLAY"
					spacer
					title={Liferay.Language.get(
						'there-are-no-individuals-found'
					)}
				/>
			);
		}
		else {
			return (
				<Table
					columns={[
						individualsListColumns.getNameJobTitle({
							channelId,
							groupId,
						}),
					]}
					items={items}
					loading={loading}
					rowIdentifier="id"
				/>
			);
		}
	}

	render() {
		const {channelId, className, groupId, id} = this.props;

		return (
			<Card className={getCN('known-individuals-card-root', className)}>
				<Card.Header>
					<Card.Title>
						{Liferay.Language.get('known-individuals')}
					</Card.Title>
				</Card.Header>

				{this.renderTable()}

				<Card.Footer>
					<ClayLink
						borderless
						button
						className="button-root"
						displayType="secondary"
						href={toRoute(Routes.CONTACTS_ACCOUNT_INDIVIDUALS, {
							channelId,
							groupId,
							id,
						})}
						small
					>
						{Liferay.Language.get('view-all-individuals')}

						<ClayIcon className="ml-2" symbol="angle-right-small" />
					</ClayLink>
				</Card.Footer>
			</Card>
		);
	}
}
