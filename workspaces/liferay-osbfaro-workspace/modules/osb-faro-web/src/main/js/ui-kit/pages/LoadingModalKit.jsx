/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import LoadingModal from '~/shared/components/modals/LoadingModal';

import Row from '../components/Row';

export default class LoadingModalKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<LoadingModal message="MyMessage" />
				</Row>

				<Row>
					<LoadingModal title="MyTitle" />
				</Row>

				<Row>
					<LoadingModal icon="embed" />
				</Row>

				<Row>
					<LoadingModal
						icon="embed"
						message="MyMessage"
						title="MyTitle"
					/>
				</Row>
			</div>
		);
	}
}
