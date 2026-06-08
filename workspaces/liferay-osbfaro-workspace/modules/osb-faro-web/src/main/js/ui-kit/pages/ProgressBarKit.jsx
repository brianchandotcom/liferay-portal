/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import ProgressBar from '~/shared/components/ProgressBar';

import Row from '../components/Row';

class ProgressBarKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<ProgressBar value={0} />
				</Row>

				<Row>
					<ProgressBar value={25} />
				</Row>

				<Row>
					<ProgressBar value={50} />
				</Row>

				<Row>
					<ProgressBar value={75} />
				</Row>

				<Row>
					<ProgressBar complete />
				</Row>
			</div>
		);
	}
}

export default ProgressBarKit;
