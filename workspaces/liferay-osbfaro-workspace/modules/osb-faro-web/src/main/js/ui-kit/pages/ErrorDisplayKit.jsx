/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {noop} from 'lodash';
import React from 'react';
import ErrorDisplay from '~/shared/components/ErrorDisplay';

export default class ErrorDisplayKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<div>
					<h3>ErrorDisplay</h3>

					<ErrorDisplay />
				</div>

				<div>
					<h3>ErrorDisplay w/ button</h3>

					<ErrorDisplay onReload={noop} />
				</div>

				<div>
					<h3>ErrorDisplay w/ button and spacer</h3>

					<ErrorDisplay onReload={noop} spacer />
				</div>
			</div>
		);
	}
}
