/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import FieldPreviewModal from '~/shared/components/modals/FieldPreviewModal';

class FieldPreviewModalKit extends React.Component {
	dataSourceFn() {
		return Promise.resolve({
			emailAddress: [
				'test@liferay.com',
				'test2@liferay.com',
				'test3@liferay.com',
			],
		});
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<FieldPreviewModal
					dataSourceFn={this.dataSourceFn}
					fieldName="emailAddress"
					sourceName="test"
				/>
			</div>
		);
	}
}

export default FieldPreviewModalKit;
