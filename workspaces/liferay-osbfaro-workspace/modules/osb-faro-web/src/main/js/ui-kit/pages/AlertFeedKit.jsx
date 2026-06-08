/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import autobind from 'autobind-decorator';
import {PropTypes} from 'prop-types';
import React from 'react';
import {connect} from 'react-redux';
import {addAlert, updateAlert} from '~/shared/actions/alerts';
import {Alert} from '~/shared/types';

import Row from '../components/Row';

const TIMEOUT = 2000;

@connect(null, {addAlert, updateAlert})
export default class AlertFeedKit extends React.Component {
	static propTypes = {
		addAlert: PropTypes.func.isRequired,
		updateAlert: PropTypes.func.isRequired,
	};

	@autobind
	handleInfo() {
		this.props.addAlert({
			alertType: Alert.Types.Default,
			message: 'You are using the offline Mode.',
		});
	}

	@autobind
	handleError() {
		this.props.addAlert({
			alertType: Alert.Types.Error,
			message: 'Upload Failed! Check your internet connection.',
		});
	}

	@autobind
	handlePending() {
		const pendingAlert = this.props.addAlert({
			alertType: Alert.Types.Pending,
			message: 'Updating your profile picture...',
		});

		const completedAlert = () => {
			this.props.updateAlert({
				alertType: Alert.Types.Success,
				id: pendingAlert.payload.id,
				message: 'Your profile picture has been updated',
				timeout: TIMEOUT,
			});
		};

		setTimeout(completedAlert, TIMEOUT);
	}

	@autobind
	handleSuccess() {
		this.props.addAlert({
			alertType: Alert.Types.Success,
			message: 'Congratulations! You are now logged in.',
		});
	}

	@autobind
	handleWarning() {
		this.props.addAlert({
			alertType: Alert.Types.Warning,
			message: `This is a warning. This alert will not go away unless you
			 dismiss it manually.`,
			timeout: false,
		});
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<ClayButton
						className="button-root"
						displayType="primary"
						onClick={this.handleInfo}
					>
						Create Info
					</ClayButton>
				</Row>

				<Row>
					<ClayButton
						className="button-root"
						displayType="primary"
						onClick={this.handleError}
					>
						Create Error
					</ClayButton>
				</Row>

				<Row>
					<ClayButton
						className="button-root"
						displayType="primary"
						onClick={this.handlePending}
					>
						Create Pending
					</ClayButton>
				</Row>

				<Row>
					<ClayButton
						className="button-root"
						displayType="primary"
						onClick={this.handleSuccess}
					>
						Create Success
					</ClayButton>
				</Row>

				<Row>
					<ClayButton
						className="button-root"
						displayType="primary"
						onClick={this.handleWarning}
					>
						Create Warning
					</ClayButton>
				</Row>
			</div>
		);
	}
}
