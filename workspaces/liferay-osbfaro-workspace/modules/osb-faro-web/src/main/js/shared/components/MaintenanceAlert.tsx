/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import getCN from 'classnames';
import moment from 'moment';
import React from 'react';
import {connect} from 'react-redux';
import {withRouter} from 'react-router-dom';
import {compose} from 'redux';
import {setMaintenanceSeen} from '~/shared/actions/maintenance-seen';
import Alert, {AlertTypes} from '~/shared/components/Alert';
import {ProjectStates} from '~/shared/util/constants';
import {sub} from '~/shared/util/lang';
import {Project} from '~/shared/util/records';

interface IMaintenanceAlertProps {
	alertDismissed: boolean;
	className: string;
	currentUserId: string;
	groupId: string;
	project: Project;
	setMaintenanceSeen: (params: any) => void;
	stripe: boolean;
}

export class MaintenanceAlert extends React.Component<IMaintenanceAlertProps> {
	static defaultProps = {
		stripe: false,
	};

	state = {
		showMessage: false,
	};

	@autobind
	handleDismissClick() {
		const {
			currentUserId,
			groupId,
			project: {stateStartDate},
			setMaintenanceSeen,
		} = this.props;

		setMaintenanceSeen({
			currentUserId,
			groupId,
			stateStartDate,
		});
	}

	render() {
		const {
			alertDismissed,
			className,
			project: {state, stateStartDate},
			stripe,
		} = this.props;

		const showAlert = state === ProjectStates.Scheduled && !alertDismissed;

		return (
			<div className={getCN('maintenance-alert-root', className)}>
				{showAlert && (
					<Alert
						iconSymbol="warning-full"
						onClose={this.handleDismissClick}
						stripe={stripe}
						title={Liferay.Language.get('scheduled-maintenance')}
						type={AlertTypes.Warning}
					>
						{sub(
							Liferay.Language.get(
								'a-system-wide-maintenance-has-been-scheduled-to-take-place-on-x-at-x'
							),
							[
								moment(stateStartDate).format('ll'),
								moment(stateStartDate).format('LT'),
							]
						)}
					</Alert>
				)}
			</div>
		);
	}
}

export const mapState = function mapState(
	store: any,
	{
		match: {
			params: {groupId},
		},
	}: any
) {
	const currentUserId = store.getIn(['currentUser', 'data']);

	const project = store.getIn(['projects', groupId, 'data'], new Project());

	const prevStateStartDate = store.getIn([
		'maintenanceSeen',
		`${groupId}-${currentUserId}`,
	]);

	return {
		alertDismissed: prevStateStartDate === project.stateStartDate,
		currentUserId,
		groupId,
		project: store.getIn(['projects', groupId, 'data'], new Project()),
	};
};

export default compose<any>(
	withRouter,
	connect(mapState, {setMaintenanceSeen})
)(MaintenanceAlert);
