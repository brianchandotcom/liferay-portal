/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import {Map, fromJS} from 'immutable';
import {PropTypes} from 'prop-types';
import React from 'react';
import {connect} from 'react-redux';
import EntityDetailsList from '~/contacts/components/EntityDetailsList';
import Card from '~/shared/components/Card';
import ErrorDisplay from '~/shared/components/ErrorDisplay';
import Loading from '~/shared/components/Loading';
import omitDefinedProps from '~/shared/util/omitDefinedProps';
import {autoCancel, hasRequest} from '~/shared/util/request-decorator';

const baseDetailsPropTypes = {
	dataSourceFn: PropTypes.func.isRequired,
	groupId: PropTypes.string.isRequired,
	id: PropTypes.string.isRequired,
	timeZoneId: PropTypes.string.isRequired,
	title: PropTypes.string,
};

@hasRequest
export class BaseDetails extends React.Component {
	static propTypes = baseDetailsPropTypes;

	state = {
		detailsIMap: new Map(),
		error: false,
		loading: true,
	};

	componentDidMount() {
		this.handleFetchDetails();
	}

	@autoCancel
	@autobind
	handleFetchDetails() {
		const {dataSourceFn, groupId, id} = this.props;

		this.setState({error: false, loading: true});

		return dataSourceFn({groupId, id})
			.then((details) =>
				this.setState({detailsIMap: fromJS(details), loading: false})
			)
			.catch((error) => {
				if (!error.IS_CANCELLATION_ERROR) {
					this.setState({
						error: true,
						loading: false,
					});
				}
			});
	}

	render() {
		const {
			props: {groupId, timeZoneId, title, ...otherProps},
			state: {detailsIMap, error, loading},
		} = this;

		if (loading) {
			return (
				<Card key="LOADING_DISPLAY" pageDisplay>
					<Loading />
				</Card>
			);
		}
		else if (error) {
			return (
				<Card key="ERROR_DISPLAY" pageDisplay>
					<ErrorDisplay
						className="flex-grow-1"
						onReload={this.handleFetchDetails}
						spacer
					/>
				</Card>
			);
		}
		else {
			return (
				<EntityDetailsList
					{...omitDefinedProps(otherProps, baseDetailsPropTypes)}
					demographicsIMap={detailsIMap}
					groupId={groupId}
					timeZoneId={timeZoneId}
					title={title}
				/>
			);
		}
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
}))(BaseDetails);
