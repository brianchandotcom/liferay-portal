/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import WorkspacesErrorDisplay, {
	NO_SUBSCRIPTION,
} from '~/shared/components/workspaces/ErrorDisplay';
import {User} from '~/shared/util/records';

export default class WorkspacesErrorDisplayKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
				style={{backgroundColor: '#F1F2F5'}}
			>
				<WorkspacesErrorDisplay
					currentUser={new User({emailAddress: 'test@liferay.com'})}
					errorType={NO_SUBSCRIPTION}
				/>
			</div>
		);
	}
}
